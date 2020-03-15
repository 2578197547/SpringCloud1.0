package cn.springcloud.book.dataservice.controller;

import cn.springcloud.book.common.util.JWTUtil;
import cn.springcloud.book.dataservice.config.DataConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author xielijie.93
 * 2020年1月25日下午9:19:14
 */
@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class DataController {
    
    @Autowired
    HttpServletRequest httpServletRequest;
	@Autowired
	private DataConfig dataConfig;
	@Value("${cn.springcloud.book.defaultUser}")
	private String defaultUser;
    
    //返回配置文件内配置的默认用户
	@RequestMapping("/getService")
    public String getService(@RequestParam(value = "request") String  request){
        return "hello:"+request;
    }
	
    //返回配置文件内配置的默认用户
    @GetMapping("/getDefaultUser")
    public String getDefaultUser(){
        return "@ConfigurationProperties:"+dataConfig.getDefaultUser()+"|@Value:"+defaultUser;
    }
    
    //返回生产者数据
    @RequestMapping("/getProviderData")
    public List<String> getProviderData(){
    	List<String> provider = new ArrayList<String>();
    	provider.add("first");
    	provider.add("second");
    	provider.add("third");
        return provider;
    }
    
    @RequestMapping("/getProviderDataByPost")
    @ResponseBody
    public List<String> getProviderDataByPost(@RequestParam String serviceName,@RequestParam String serviceValue){
    	List<String> provider = new ArrayList<String>();
    	provider.add(serviceName);
    	provider.add(serviceValue);
        return provider;
    }
    
    @RequestMapping("/getTokenDetail")
    public Map<String, Object> getTokenDetail(){
    	Map<String, Object> claimsMap = new HashMap<String, Object>();
        try {
        	claimsMap = JWTUtil.analysisReq(httpServletRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return claimsMap;
    }
    
    /**
     * redis sesion共享
     * @param request
     * @return
     */
    @RequestMapping("/getUser")
    public String getUser() {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> claimsMap = (Map<String, Object>) session.getAttribute("claimsMap");
        System.out.println("访问端口：" + httpServletRequest.getServerPort());
        return "SessionId:"+session.getId()+"|SysUser:"+claimsMap;
    }
    
}
