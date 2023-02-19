package com.janty.dao;

import com.janty.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 17:08
 * @description:
 */
public interface AdminRoleDao extends BaseDao<AdminRole>{

    List<Long> findRoleIdsByAdminId(Long adminId);

    void deleteRoleIdsByAdminId(Long adminId);

    void addRoleIdAndAdminId(@Param("roleId") Long roleId, @Param("adminId") Long adminId);

}
