package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.Admin;
import com.janty.entity.Permission;
import com.janty.service.AdminService;
import com.janty.service.PermissionService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/11 17:02
 * @description:
 */

@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

//    @RequestMapping("/")
//    public String toIndex(){
//        return "frame/index";
//    }

    //去首页
    @RequestMapping("/")
    public String toIndex(Map map){
//        Long userId = 1l;                                                                            //静态获取用户名
//        Admin admin = adminService.getById(userId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();      //动态获取用户名
        Admin admin = adminService.getAdminByUsername(user.getUsername());
        List<Permission> permissionList = permissionService.getMenuPermissionByAdminId(admin.getId());
        map.put("admin",admin);
        map.put("permissionList",permissionList);

        return "frame/index";
    }

    @RequestMapping("/main")
    public String toMain(){
        return "frame/main";
    }

    @RequestMapping("/login")
    public String toLogin(){
        return "frame/login.html";
    }

    @RequestMapping("/auth")            //前往无权限的页面
    public String toAuth(){
        return "frame/auth";
    }

}
