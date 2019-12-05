package cn.springcloud.book.auth.service;

import java.util.List;
import cn.springcloud.book.auth.entity.Permission;

public interface PermissionService {
	public List<Permission> findAll();

	public List<Permission> findByAdminUserId(int userId);
}
