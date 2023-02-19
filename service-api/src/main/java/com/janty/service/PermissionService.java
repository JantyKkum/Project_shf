package com.janty.service;

import com.janty.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 18:13
 * @description:
 */

public interface PermissionService extends BaseService<Permission>{
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    void assignPermission(@Param("roleId") Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<String> getPermissionCodesByAdminId(Long id);

}
