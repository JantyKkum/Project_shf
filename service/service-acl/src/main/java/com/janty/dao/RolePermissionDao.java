package com.janty.dao;

import com.janty.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 18:17
 * @description:
 */
public interface RolePermissionDao extends BaseDao<RolePermission>{
    List<Long> findPermissionIdsByRoleId(Long roleId);

    void deletePermissionIdsByRoleId(Long roleId);

    void addRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

}
