package com.janty.service;

import com.janty.entity.Admin;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/12 15:16
 * @description:
 */
public interface AdminService extends BaseService<Admin>{
    List<Admin> findAll();

    Admin getAdminByUsername(String username);
}
