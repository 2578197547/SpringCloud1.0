package cn.springcloud.book.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import cn.springcloud.book.user.service.UserService;
import cn.springcloud.book.user.utils.BPwdEncoderUtil;

@RestController
public class TestEndPointController {

    Logger logger = LoggerFactory.getLogger(TestEndPointController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    //-------------------------------开放接口-begin-------------------------------
    @RequestMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        String dbpasswor = "$2a$10$HBX6q6TndkgMxhSEdoFqWOUtctaJEMoXe49NWh8Owc.4MTunv.wXa";
        logger.info("判断两个密码是否相等 " + (BPwdEncoderUtil.matches("123456", dbpasswor)));
        return "product id : " + id;
    }
    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "order id : " + id;
    }
    //-------------------------------开放接口-end-------------------------------

    //-------------------------------Security权限控制-begin-------------------------------
    //@PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping("/hello")
    public String hello() {
        return "hello you.by ROLE_ADMIN";
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/helloRole")
    public String helloRole() {
        return "hello you.by ADMIN";
    }
    //-------------------------------Security权限控制-end-------------------------------
    
    //-------------------------------Feign获取服务-begin-------------------------------
	@RequestMapping("/getService")
	public String getService(@RequestParam(value = "request") String  request) {
        return userService.getService(request);
    }
	/**
	 * 获取配置文件中系统默认用户
	 * @return
	 */
    @GetMapping("/getDefaultUser")
    public String getDefaultUser(){
        return userService.getDefaultUser();
    }
    
    @RequestMapping("/getUser")
    public String getUser(){
    	return userService.getUser();
    }
    //-------------------------------Feign获取服务-end-------------------------------
    
    //-------------------------------restTemplate获取服务-begin-------------------------------
    @RequestMapping("/getProviderData")
    public List<String> getProviderData(HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("SysUser");
        username = "testSessionRedis1|" + System.currentTimeMillis();
        session.setAttribute("SysUser", username);
        System.out.println(session.getId()+"------------"+username);
        return userService.getProviderData();
    }
    
    @RequestMapping("/getProviderDataByPost")
    public List<String> getProviderDataByPost(HttpServletRequest request){
        return userService.getProviderDataByPost();
    }
    
    @RequestMapping("/getUserByRestTemplate")
    public String getUserByRestTemplate() {
    	return userService.getUserByRestTemplate();
    }
    //-------------------------------restTemplate获取服务-end-------------------------------

    //-------------------------------StringRedisTemplate/RedisTemplate缓存测试-begin-------------------------------
    @RequestMapping("/redisTemplateTest")
    public String redisTemplateTest() {
    	stringRedisTemplate.opsForValue().set("stringRedisTemplate", "set-stringRedisTemplate");
    	Map<String,Object> map1 = new HashMap<String,Object>();
    	map1.put("1", System.currentTimeMillis());
    	map1.put("2", "redisTemplate2");
    	redisTemplate.opsForValue().set("redisTemplate1", map1);
        if(!redisTemplate.hasKey("redisTemplate2")){
        	Map<String,Object> map2 = new HashMap<String,Object>();
        	map2.put("1", System.currentTimeMillis());
        	map2.put("2", "redisTemplate2");
        	redisTemplate.opsForValue().set("redisTemplate2", map2,30, TimeUnit.SECONDS);
        }
    	return "stringRedisTemplate:"+stringRedisTemplate.opsForValue().get("stringRedisTemplate")
    			+"<br/>"
    			+"|redisTemplate1:"+redisTemplate.opsForValue().get("redisTemplate1")
    			+"<br/>"
    			+"|redisTemplate2:"+redisTemplate.opsForValue().get("redisTemplate2");
    }
    //-------------------------------StringRedisTemplate/RedisTemplate缓存测试-end-------------------------------
    
    
    
    //-------------------------------redis缓存测试-begin-------------------------------
    @RequestMapping("/getRedisData")
    @Cacheable(value="redis-data", keyGenerator="keyGenerator")
    public String getRedisData() {
    	System.out.println("出现该提示说明未调用redis缓存");
    	return new String("getRedisData:"+System.currentTimeMillis());
    }
    //-------------------------------redis缓存测试-end-------------------------------
}
