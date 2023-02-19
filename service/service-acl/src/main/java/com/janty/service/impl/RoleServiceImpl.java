package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.AdminRoleDao;
import com.janty.dao.BaseDao;
import com.janty.dao.RoleDao;
import com.janty.entity.Role;
import com.janty.service.Impl.BaseServiceImpl;
import com.janty.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/11 14:48
 * @description:
 */

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
//    @Reference
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        List<Role> roleList = roleDao.findAll();
        List<Long> roleIds = adminRoleDao.findRoleIdsByAdminId(adminId);        //用户已拥有角色的id
        //创建两个List（已分配和未分配）
        List<Role> noAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : roleList) {                                            //遍历所有角色
            if(roleIds.contains(role.getId())){                                 //已分配
                assignRoleList.add(role);
            }else {                                                             //未分配
                noAssignRoleList.add(role);
            }
        }
        Map<String,Object> roleMap = new HashMap<>();                           //创建返回map
        roleMap.put("noAssignRoleList",noAssignRoleList);
        roleMap.put("assignRoleList",assignRoleList);
        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteRoleIdsByAdminId(adminId);                           //根据用户id将已分配角色删除
        for (Long roleId : roleIds) {
            if(roleId != null) {
                adminRoleDao.addRoleIdAndAdminId(roleId,adminId);
            }
        }
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }



}
