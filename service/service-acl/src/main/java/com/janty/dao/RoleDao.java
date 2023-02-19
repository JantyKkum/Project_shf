package com.janty.dao;

import com.janty.entity.Role;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/11 14:40
 * @description:
 */
public interface RoleDao extends BaseDao{
    List<Role> findAll();


//    Integer insert(Role role);
//
//    void delete(Long roleId);
//
//    Integer edit(Long roleId);
//
//    Role getById(Long roleId);
//
//    Integer update(Role role);
//
//    Page<Role> findPage(Map<String, Object> filters);
}
