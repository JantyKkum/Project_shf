package com.janty.dao;


import com.janty.entity.Permission;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 18:14
 * @description:
 */
public interface PermissionDao extends BaseDao<Permission>{
    List<Permission> findAll();

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<String> getAllPermissionCodes();

    List<String> getPermissionCodesByAdminId(Long id);

}
