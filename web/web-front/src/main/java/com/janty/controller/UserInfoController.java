package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.UserInfo;
import com.janty.result.Result;
import com.janty.result.ResultCodeEnum;
import com.janty.service.UserInfoService;
import com.janty.util.MD5;
import com.janty.vo.LoginVo;
import com.janty.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/15 18:43
 * @description:
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpServletRequest request){
        //设置验证码
        String code = "8888";
        request.getSession().setAttribute("code",code);
        return Result.ok(code);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession httpSession){
        //获取
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        if(StringUtils.isEmpty(phone) ||
           StringUtils.isEmpty(password) ||
           StringUtils.isEmpty(nickName) ||
           StringUtils.isEmpty(code)
        ) return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        String sessionCode = (String) httpSession.getAttribute("code");
        if(!code.equals(sessionCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo =  userInfoService.getUserInfoByPhone(phone);
        if(userInfo != null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPhone(phone);
        userInfo1.setNickName(nickName);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setStatus(1);

        userInfoService.insert(userInfo1);
        return Result.ok();
    }


    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpSession session){
        //获取用户电话和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if(userInfo == null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }

        if(!MD5.encrypt(password).equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }

        if(userInfo.getStatus() == 0){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //登陆成功
        //把用户信息保存到session域，以便后台判断
        session.setAttribute("user",userInfo);
        //创建map，设置nickname
        Map map = new HashMap<>();
        map.put("nickName",userInfo.getNickName());
        map.put("phone",phone);
        return Result.ok(map);
    }

    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        session.removeAttribute("user");
        return Result.ok();
    }
}
