package cn.springcloud.book.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.springcloud.book.user.entity.Permission;
import cn.springcloud.book.user.entity.SysRole;
import cn.springcloud.book.user.entity.SysUser;
import cn.springcloud.book.user.repository.PermissionRepository;
import cn.springcloud.book.user.repository.SysRoleRepository;
import cn.springcloud.book.user.repository.SysUserRepository;
import cn.springcloud.book.user.service.UserService;
import cn.springcloud.book.user.service.feignService.servie.DataService;

@Service
public class UserServiceImpl implements UserService {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DataService dataService;
	@Autowired
	SysUserRepository sysUserRepository;
	@Autowired
	SysRoleRepository sysRoleRepository;
	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public SysUser getUserByName(String name) {
		SysUser sysUser = sysUserRepository.getUserByName(name);
		List<SysRole> list = sysRoleRepository.getRoleListByUserName(name);
		sysUser.setRoles(list);
		return sysUser;
	}
	
	@Override
	public List<Permission> getPermissionsByName(String name) {
		List<SysRole> list = sysRoleRepository.getRoleListByUserName(name);
		List<Permission> resultList = new ArrayList<Permission>();
		for(SysRole role:list){
			if(role!=null){
				List<Permission> clist = permissionRepository.getPermissionListByRoleId(role.getId());
				if(clist.size()>0)resultList.addAll(clist);
			}
		}
		return resultList;
	}
	
    @Override
    @Transactional
    public SysUser create(String username, String password) {
    	if(sysUserRepository.getUserByName(username) == null){
        	SysUser user = new SysUser();
            user.setName(username);
            password = passwordEncoder.encode(password);
            user.setPassword(password);
            user = sysUserRepository.save(user);
            return user;
    	}else{
    		return null;
    	}
    }
    
	@Override
	public String getService(String request) {
		return dataService.getService(request);
	}
    
	@Override
	public String getDefaultUser() {
		return dataService.getDefaultUser();
	}
	
	@Override
	public String getUser() {
		return dataService.getUser();
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "getProviderDataError")
	public List<String> getProviderData() {
		List<String> result = restTemplate.getForObject("http://sc-data-service/getProviderData", List.class);
		return result;
	}
	public List<String> getProviderDataError() {
		List<String> list = new ArrayList<String>();
		list.add("getFail");
		return list;
	}

	@Override
	@HystrixCommand(fallbackMethod = "getProviderDataByPostError")
	public List<String> getProviderDataByPost() {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		param.add("serviceName", "serviceName");
		param.add("serviceValue", "serviceValue");
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/x-www-form-urlencoded");
//		HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(param, headers);
		//含有请求头
		//List<String> result = restTemplate.postForObject("http://sc-data-service/getProviderDataByPost", formEntity, List.class);
		//不含请求头
		List<String> result = restTemplate.postForObject("http://sc-data-service/getProviderDataByPost", param, List.class);
		return result;
	}
	public List<String> getProviderDataByPostError() {
		List<String> list = new ArrayList<String>();
		list.add("getFail");
		return list;
	}

	@Override
	@HystrixCommand(fallbackMethod = "getUserByRestTemplateError")
	public String getUserByRestTemplate() {
		return restTemplate.postForObject("http://sc-data-service/getUser", null, String.class);
	}
	public String getUserByRestTemplateError() {
		return new String("getUserByRestTemplateError");
	}
}
