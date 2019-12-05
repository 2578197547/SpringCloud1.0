package cn.springcloud.book.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cn.springcloud.book.user.entity.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission,Integer> {
 
}
