package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.janty.entity.Admin;
import com.janty.service.AdminService;
import com.janty.service.RoleService;
import com.janty.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/12 15:15
 * @description:
 */

@Controller
@RequestMapping(value ="/admin")
public class AdminController extends BaseController{

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //分页及带条件查询方法
    @RequestMapping
    public String findPage(Map map,HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        map.put("filters",filters);
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        map.put("page",pageInfo);
        return "admin/index";
    }

    @RequestMapping("/create")
    public String goAddPage(){
        return "admin/create";
    }

    //保存用户
    @RequestMapping("/save")
    public String save(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));     //密码加密
        adminService.insert(admin);
        return "common/successPage";
    }

    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId){
        adminService.delete(adminId);
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{adminId}")
    public String goEditPage(@PathVariable("adminId") Long adminId,Map map){
        Admin admin = adminService.getById(adminId);
        map.put("admin",admin);
        return "admin/edit";
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return "common/successPage";
    }

    @RequestMapping("/uploadShow/{id}")
    public String goUploadPage(@PathVariable("id") Long id, Map map){
        map.put("id",id);
        return "admin/upload";
    }

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, MultipartFile file){
        try {
            Admin admin = adminService.getById(id);
            byte[] bytes = file.getBytes();
            String fileName = UUID.randomUUID().toString();
            QiniuUtils.upload2Qiniu(bytes,fileName);
            admin.setHeadUrl("http://rq28dhr3f.hn-bkt.clouddn.com/" + fileName);
            adminService.update(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "common/successPage";
    }

    @RequestMapping("/assignShow/{adminId}")
    public String goAssignShowPage(@PathVariable("adminId") Long adminId, ModelMap modelMap){
        modelMap.addAttribute("adminId",adminId);
        //调用roleService
        Map<String, Object> roleByAdminId = roleService.findRoleByAdminId(adminId);
        modelMap.addAllAttributes(roleByAdminId);
        return "admin/assignShow";
    }

    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds){
        //调用roleService分配角色方法
        roleService.assignRole(adminId,roleIds);
        return "common/successPage";
    }

}



