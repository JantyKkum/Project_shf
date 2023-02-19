package com.janty.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.Admin;
import com.janty.service.AdminService;
import com.janty.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/17 12:02
 * @description:
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    //登录时，spring security 调用此方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUsername(username);
        if (admin == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        List<String> permissionCodes = permissionService.getPermissionCodesByAdminId(admin.getId());           //调用permissionService查询用户权限
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();                                         //创建一个用户授权的集合
        for (String permissionCode : permissionCodes) {
            if(!StringUtils.isEmpty(permissionCode)){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
                grantedAuthorities.add(simpleGrantedAuthority);
            }
        }
//        return new User(username,admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return new User(username,admin.getPassword(), grantedAuthorities);
    }
}
