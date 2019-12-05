package cn.springcloud.book.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cn.springcloud.book.auth.entity.SysRole;

@Repository
public interface SysRoleRepository extends CrudRepository<SysRole, Integer> {

}
