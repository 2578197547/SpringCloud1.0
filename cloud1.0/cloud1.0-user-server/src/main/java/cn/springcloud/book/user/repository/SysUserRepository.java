package cn.springcloud.book.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cn.springcloud.book.user.entity.SysUser;

@Repository
public interface SysUserRepository extends CrudRepository<SysUser,Integer> {
 
    @Query("select a from SysUser a where a.name=:name")
    public SysUser getUserByName(@Param("name") String name);
}
