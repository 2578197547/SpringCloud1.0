package cn.springcloud.book.auth.service;

import cn.springcloud.book.auth.entity.SysUser;

public interface SysUserService {
	public SysUser getUserByName(String name);
}
