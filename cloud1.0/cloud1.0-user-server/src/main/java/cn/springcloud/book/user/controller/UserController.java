package cn.springcloud.book.user.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.springcloud.book.common.util.JWTUtil;
import cn.springcloud.book.user.dto.UserLoginParamDto;
import cn.springcloud.book.user.entity.Permission;
import cn.springcloud.book.user.entity.SysUser;
import cn.springcloud.book.user.repository.SysUserRepository;
import cn.springcloud.book.user.service.UserService;
import cn.springcloud.book.user.utils.BPwdEncoderUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/registry")
    public SysUser createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            return userService.create(username, password);
        }
        return null;
    }
    
    /**
     * 用户登陆，获取token后sessionId改变
     * @param loginDto
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    @HystrixCommand(fallbackMethod = "loginError")
    public ResponseEntity<OAuth2AccessToken> login(@Valid UserLoginParamDto loginDto, BindingResult bindingResult,HttpServletRequest request) throws Exception {

        if (bindingResult.hasErrors())
            throw new Exception("登录信息错误，请确认");
        String username = loginDto.getUsername();
        SysUser user = userService.getUserByName(username);
        if (null == user)
            throw new Exception("用户为空");
        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), user.getPassword()))
            throw new Exception("密码错误");

        String client_secret = oAuth2ClientProperties.getClientId()+":"+oAuth2ClientProperties.getClientSecret();
        client_secret = "Basic "+Base64.getEncoder().encodeToString(client_secret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",client_secret);
        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(username));//Collections.singletonList:用于只有一个元素的优化，减少内存分配
        map.put("password", Collections.singletonList(loginDto.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
        //获取 Token(注意先配置security)

        return restTemplate.exchange("http://sc-auth-server/oauth/token", HttpMethod.POST,httpEntity,OAuth2AccessToken.class);//访问/oauth/token获取令牌
    }
    
    //登陆熔断
    public ResponseEntity<OAuth2AccessToken> loginError(@Valid UserLoginParamDto loginDto, BindingResult bindingResult,HttpServletRequest request) throws Exception {
    	throw new Exception("登陆服务访问失败");
    }
    
    @RequestMapping("/setSession")
    public Map<String,String> setSession(HttpServletRequest request){
    	Map<String, String> result = new HashMap<String, String>();
		try {
			HttpSession session = request.getSession();
			Map<String, Object> claimsMap = new HashMap<String, Object>();
			//如果通信中包含令牌，将令牌解析成用户信息保存到上下文
			String access_token = request.getParameter("access_token");
			String Authorization = request.getHeader("Authorization");
			if(access_token!=null&&access_token.length()>1){
				claimsMap = JWTUtil.jwtVerify(access_token);
			}else if (Authorization!=null&&Authorization.length()>1&&Authorization.toUpperCase().startsWith("BEARER")) {
				Authorization = Authorization.replaceFirst("(?i)bearer(\\s*)", "");//bearer(\\s*)忽视大小写
				claimsMap = JWTUtil.jwtVerify(Authorization);
			}
			claimsMap.put("setTime", System.currentTimeMillis());
			session.setAttribute("claimsMap", claimsMap);
			result.put("O_CODE", "1");
			result.put("O_NOTE", "SUCCED");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("O_CODE", "-1");
			result.put("O_NOTE", "FAILED");
		}
    	return result;
    }
}
