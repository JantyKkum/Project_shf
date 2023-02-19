package com.janty.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.BaseDao;
import com.janty.dao.PermissionDao;
import com.janty.dao.RolePermissionDao;
import com.janty.entity.Permission;
import com.janty.helper.PermissionHelper;
import com.janty.service.Impl.BaseServiceImpl;
import com.janty.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 18:13
 * @description:
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService{

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return null;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //map数据格式 { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
        List<Permission> permissionList = permissionDao.findAll();
        List<Long> permissionIds = rolePermissionDao.findPermissionIdsByRoleId(roleId);
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (Permission permission : permissionList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            if(permissionIds.contains(permission.getId())){
                map.put("checked", true);
            }
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        rolePermissionDao.deletePermissionIdsByRoleId(roleId);
        for (Long permissionId : permissionIds) {
            if(permissionId != null){
                rolePermissionDao.addRoleIdAndPermissionId(roleId,permissionId);
            }
        }
    }

    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        List<Permission> permissionList = null;
        if(userId == 1){                                        //系统管理员
            permissionList = permissionDao.findAll();
        }else {                                                //非系统管理员
            permissionList = permissionDao.getMenuPermissionByAdminId(userId);
        }
        return PermissionHelper.bulid(permissionList);
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodes;
        if(id == 1){                                        //系统管理员
            permissionCodes = permissionDao.getAllPermissionCodes();
        }else {                                             //非系统管理员
            permissionCodes = permissionDao.getPermissionCodesByAdminId(id);
        }
        return permissionCodes;
    }
}
