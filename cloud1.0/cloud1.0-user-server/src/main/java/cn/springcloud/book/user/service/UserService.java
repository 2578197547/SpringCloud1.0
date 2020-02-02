package cn.springcloud.book.user.service;

import java.util.List;

import cn.springcloud.book.user.entity.Permission;
import cn.springcloud.book.user.entity.SysUser;

public interface UserService {

	public SysUser getUserByName(String name);
	
	public List<Permission> getPermissionsByName(String name);
	
	SysUser create(String username, String password);
	
	public String getService(String request);
	
	public String getDefaultUser();
	
	public List<String> getProviderData();
	
	public List<String> getProviderDataByPost();
	
	public String getUser();

	public String getUserByRestTemplate();
}
