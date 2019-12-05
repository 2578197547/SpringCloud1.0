package cn.springcloud.book.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cn.springcloud.book.user.entity.SysRole;


@Repository
public interface SysRoleRepository extends CrudRepository<SysRole,Integer> {
	
}
