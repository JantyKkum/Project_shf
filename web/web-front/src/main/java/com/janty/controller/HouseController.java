package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.janty.entity.*;
import com.janty.result.Result;
import com.janty.service.*;
import com.janty.vo.HouseQueryVo;
import com.janty.vo.HouseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/15 16:42
 * @description:
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }

    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session){
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseImage> houseImage1List = houseImageService.getHouseImageByHouseIdAndType(id, 1);
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(id);
        Map map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseImage1List",houseImage1List);
        if(houseBrokerList != null) map.put("houseBrokerList",houseBrokerList);
//        map.put("isFollow",false);
        Boolean isFollowed = false;
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if(userInfo != null){               //表示已登录
            isFollowed = userFollowService.isFollowed(userInfo.getId(),id);
        }
        map.put("isFollowed",isFollowed);
        return Result.ok(map);
    }
}
