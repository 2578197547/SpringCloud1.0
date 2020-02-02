package cn.springcloud.book.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.springcloud.book.user.entity.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission,Integer> {
	@Query(value="SELECT p.* FROM sys_permission_role pr "
			+ "LEFT JOIN sys_permission p ON p.id = pr.permission_id "
			+ "WHERE pr.role_id = :roleId ",nativeQuery = true)
	public List<Permission> getPermissionListByRoleId(@Param("roleId") Integer roleId);
}
