package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.janty.entity.UserInfo;
import com.janty.result.Result;
import com.janty.service.UserFollowService;
import com.janty.vo.UserFollowVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 14:09
 * @description:
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController{

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用userFollowService.follow
        userFollowService.follow(userInfo.getId(),houseId);
        return Result.ok();
    }

    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result myFollowed(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             HttpSession session){
        UserInfo userInfo  = (UserInfo) session.getAttribute("user");
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum,pageSize,userInfo.getId());
        return Result.ok(pageInfo);
    }

    //取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id){
        userFollowService.cancelFollow(id);
        return Result.ok();
    }


}
