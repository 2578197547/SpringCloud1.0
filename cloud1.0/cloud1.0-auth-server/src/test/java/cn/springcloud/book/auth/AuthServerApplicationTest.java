package cn.springcloud.book.auth;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.springcloud.book.auth.entity.SysRole;
import cn.springcloud.book.auth.entity.SysUser;
import cn.springcloud.book.auth.repository.SysRoleRepository;
import cn.springcloud.book.auth.repository.SysUserRepository;
import cn.springcloud.book.auth.service.SysUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServerApplicationTest {

	@Autowired
	SysUserRepository sysUserRepository;
	@Autowired
	SysRoleRepository sysRoleRepository;
	@Autowired
	SysUserService sysUserService;
/*    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }*/
    @Test
    public void myTest(){
/*    	SysUser sysUser = sysUserRepository.getUserByName("admin");
    	for(SysRole role:sysUser.getRoles()){
    		System.out.println(role.getName());
    	}*/
    	SysUser User = sysUserService.getUserByName("OneTest");
    	System.out.println(User.getName());
    	for(SysRole role:User.getRoles()){
    		System.out.println(role.getName());
    	}
/*    	List<SysRole> list = sysRoleRepository.getRoleListByUserName("admin");
    	for(SysRole role:list){
    		System.out.println(role.getName());
    	}*/
    }
}
