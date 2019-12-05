package cn.springcloud.book.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.springcloud.book.user.entity.SysUser;
import cn.springcloud.book.user.repository.SysUserRepository;
import cn.springcloud.book.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    SysUserRepository sysUserRepository;

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
}
