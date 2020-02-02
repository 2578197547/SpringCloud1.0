package cn.springcloud.book.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.springcloud.book.auth.entity.SysRole;

@Repository
public interface SysRoleRepository extends CrudRepository<SysRole, Integer> {
	
	@Query(value="SELECT r.* FROM sys_user u "
			+ "LEFT JOIN sys_role_user ru ON ru.sys_user_id = u.id "
			+ "LEFT JOIN sys_role r ON r.id = ru.sys_role_id "
			+ "WHERE u.name = :name",nativeQuery = true)
	public List<SysRole> getRoleListByUserName(@Param("name") String name);
}
