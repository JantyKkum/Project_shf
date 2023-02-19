package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.janty.entity.Role;
import com.janty.service.PermissionService;
import com.janty.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/11 14:49
 * @description:
 */

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController{

    public static final String SUCCESS = "common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    //首页
//    @RequestMapping
//    public String index(Map map){
//        List<Role> roleList = roleService.findAll();
//        map.put("list",roleList);
//        return "role/index";
//    }

    //分页及带条件查询方法
    @PreAuthorize("hasAuthority('role.show')")                 //只有增加了role.show权限的用户才能调用此方法
    @RequestMapping
    public String index(Map map,HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        map.put("filters",filters);
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        map.put("page",pageInfo);
        return "role/index";
    }

    //前往添加页面
    @PreAuthorize("hasAuthority('role.create')")                 //只有增加了权限的用户才能调用此方法
    @RequestMapping("/create")
    public String toCreatePage(){
        return "role/create";
    }

    //添加用户
    @PreAuthorize("hasAuthority('role.create')")                 //只有增加了权限的用户才能调用此方法
    @RequestMapping("/save")
    public String SaveRole(Role role){
        roleService.insert(role);
//        return "redirect:/role";
        return SUCCESS;
    }

    //删除用户
    @PreAuthorize("hasAuthority('role.delete')")                 //只有增加了role.delete权限的用户才能调用此方法
    @RequestMapping("/delete/{roleId}")
    public String deleteRole(@PathVariable("roleId") Long roleId){
        roleService.delete(roleId);
        return "redirect:/role";
    }

    //通过id获取用户
    @PreAuthorize("hasAuthority('role.edit')")                 //只有增加了权限的用户才能调用此方法
    @RequestMapping("/edit/{id}")
    public String toeditPage(@PathVariable("id") Long id,Map map){
        Role role = (Role) roleService.getById(id);
        map.put("role",role);
        return "role/edit";
    }

    //更新用户
    @PreAuthorize("hasAuthority('role.edit')")                 //只有增加了权限的用户才能调用此方法
    @RequestMapping("/update")
    public String updateRole(Role role){
        roleService.update(role);
        return SUCCESS;
    }

    /**
     * 封装页面提交的分页参数及搜索条件
     * @param request
     * @return
     */
    public Map<String, Object> getFilters(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 3);
        }

        return filters;
    }

    //分配权限页面
    @PreAuthorize("hasAuthority('role.assgin')")                 //只有增加了权限的用户才能调用此方法
    @RequestMapping("/assignShow/{roleId}")
    public String goAssignShowPage(@PathVariable("roleId") Long roleId, Map map){
        map.put("roleId",roleId);
        List<Map<String,Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        map.put("zNodes", zNodes);
        return "role/assignShow";
    }

    //分配权限
    @PreAuthorize("hasAuthority('role.assign')")                 //只有增加了权限的用户才能调用此方法
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,@RequestParam("permissionIds") Long[] permissionIds){
        permissionService.assignPermission(roleId,permissionIds);
        return SUCCESS;
    }
}
