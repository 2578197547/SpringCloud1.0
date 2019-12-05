package cn.springcloud.book.user.service;

import cn.springcloud.book.user.entity.SysUser;

public interface UserService {

	SysUser create(String username, String password);

}
