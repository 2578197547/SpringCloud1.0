package cn.springcloud.book.user.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cn.springcloud.book.user.dto.UserLoginParamDto;
import cn.springcloud.book.user.entity.SysUser;
import cn.springcloud.book.user.repository.SysUserRepository;
import cn.springcloud.book.user.utils.BPwdEncoderUtil;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Collections;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/login")
    @HystrixCommand(fallbackMethod = "loginError")
    public ResponseEntity<OAuth2AccessToken> login(@Valid UserLoginParamDto loginDto, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors())
            throw new Exception("登录信息错误，请确认后再试");

        SysUser user = sysUserRepository.getUserByName(loginDto.getUsername());

        if (null == user)
            throw new Exception("用户为空，出错了");

        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), user.getPassword()))
            throw new Exception("密码不正确");

        String client_secret = oAuth2ClientProperties.getClientId()+":"+oAuth2ClientProperties.getClientSecret();

        client_secret = "Basic "+Base64.getEncoder().encodeToString(client_secret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",client_secret);

        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginDto.getUsername()));
        map.put("password", Collections.singletonList(loginDto.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));

        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
        //获取 Token(注意先配置security)
        return restTemplate.exchange("http://sc-auth-server/oauth/token", HttpMethod.POST,httpEntity,OAuth2AccessToken.class);//访问/oauth/token获取令牌

    }
    
    //登陆熔断
    public ResponseEntity<OAuth2AccessToken> loginError(@Valid UserLoginParamDto loginDto, BindingResult bindingResult) throws Exception {
    	throw new Exception("登陆服务访问失败");
    }
}
