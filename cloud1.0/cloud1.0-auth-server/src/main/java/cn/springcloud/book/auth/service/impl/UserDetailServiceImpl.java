package cn.springcloud.book.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.springcloud.book.auth.entity.Permission;
import cn.springcloud.book.auth.entity.SysUser;
import cn.springcloud.book.auth.repository.SysUserRepository;
import cn.springcloud.book.auth.service.PermissionService;

/**
 * 自定义userdetailservice
 * @author xielijie.93
 * 2019年12月5日上午11:57:33
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	SysUserRepository sysUserRepository;
	@Autowired
	PermissionService permissionService;
	@Autowired
	PasswordEncoder passwordEncoder;// cn.springcloud.book.auth.config.SecurityConfiguration>BCryptPasswordEncoder

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		SysUser sysUser = sysUserRepository.getUserByName(username);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (sysUser != null) {
			System.err.println("sysUser===============" + sysUser);
			// 获取用户的授权
			List<Permission> permissions = permissionService
					.findByAdminUserId(sysUser.getId());
			// 声明授权文件
			for (Permission permission : permissions) {
				if (permission != null && permission.getName() != null) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
							"ROLE_" + permission.getName());// Security中权限名称必须满足ROLE_XXX
					grantedAuthorities.add(grantedAuthority);
				}
			}
		}
		System.err.println("grantedAuthorities==============="+ grantedAuthorities);
		return new User(sysUser.getName(), sysUser.getPassword(),
				grantedAuthorities);
	}
}
