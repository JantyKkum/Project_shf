package com.janty.service;

import com.janty.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/11 14:47
 * @description:
 */
public interface RoleService extends BaseService<Role>{

    List<Role> findAll();

    //根据用户id查询用户角色
    Map<String,Object> findRoleByAdminId(Long id);

    void assignRole(Long adminId, Long[] roleIds);

//
//    Integer insert(Role role);
//
//    void delete(Long roleId);
//
//    Role getById(Long roleId);
//
//    Integer update(Role role);
//
//    PageInfo<Role> findPage(Map<String, Object> filters);
}

