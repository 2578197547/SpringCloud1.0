package cn.springcloud.book.auth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.springcloud.book.auth.entity.SysRole;
import cn.springcloud.book.auth.entity.SysUser;
import cn.springcloud.book.auth.repository.SysRoleRepository;
import cn.springcloud.book.auth.repository.SysUserRepository;
import cn.springcloud.book.auth.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserRepository sysUserRepository;
	@Autowired
	SysRoleRepository sysRoleRepository;

	@Override
	public SysUser getUserByName(String name) {
		SysUser sysUser = sysUserRepository.getUserByName(name);
		List<SysRole> list = sysRoleRepository.getRoleListByUserName(name);
		sysUser.setRoles(list);
		return sysUser;
	}

}
