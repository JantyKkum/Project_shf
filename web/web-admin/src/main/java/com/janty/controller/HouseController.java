package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.janty.entity.*;
import com.janty.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 15:26
 * @description:
 */

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private HouseBrokerService houseBrokerService;


    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);

        PageInfo<House> pageInfo = houseService.findPage(filters);
        map.put("page",pageInfo);
        //获取小区...
        extracted(map);

        map.put("filters",filters);
        return "house/index";
    }

    @RequestMapping("/create")
    public String goAddPage(Map map){
        extracted(map);

        return "house/create";
    }

    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id,Map map){
        House house = houseService.getById(id);
        map.put("house",house);
        extracted(map);
        return "house/edit";
    }

    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable("houseId") Long houseId,@PathVariable("status")Integer status){
        houseService.publish(houseId,status);
        return "redirect:/house";
    }

    private void extracted(Map map) {
        List<Community> communityList = communityService.findAll();
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        List<Dict> floorList = dictService.findListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        List<Dict> directionList = dictService.findListByDictCode("direction");
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList ", houseUseList);
    }

    //更新
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete (@PathVariable("id") Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    @RequestMapping("/detail/{houseId}")
    public String show(@PathVariable("houseId") Long houseId, Map map){
        House house = houseService.getById(houseId);
        map.put("house",house);
        Community community = communityService.getById(house.getCommunityId());
        map.put("community",community);
        //房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImageByHouseIdAndType(houseId, 1);
        List<HouseImage> houseImage2List = houseImageService.getHouseImageByHouseIdAndType(houseId, 2);
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(houseId);
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(houseId);

        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);

        return "house/detail";
    }

//    //查看详情
//    @RequestMapping("/detail/{id}")
//    public String detail(Map map,@PathVariable Long id){
//        //1.房源对象
//        //在业务层，重写getById方法，关联字典名称
//        House house = houseService.getById(id);
//        //2.小区对象
//        //在业务层重写，获取小区名字
//        Community community = communityService.getById(house.getCommunityId());
//        //3.普通房源图片集合
//        //表示普通房源图片
//        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
//
//        //4.房产图片集合
//        //查询房产图片，不能在前端系统显示，只能让后台管理员看到
//        List<HouseImage> houseImage2List = houseImageService.findList(id, 2);
//
//        //4.1(新增) 房产首页默认图片
//        List<HouseImage> houseImage3List = houseImageService.findList(id, 3);
//
//        //5.经纪人集合
//        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
//
//        //6.房东集合
//        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);
//        map.put("house",house);
//        map.put("community",community);
//        map.put("houseImage1List",houseImage1List);
//        map.put("houseImage2List",houseImage2List);
//        map.put("houseImage3List",houseImage3List);
//        map.put("houseBrokerList",houseBrokerList);
//        map.put("houseUserList",houseUserList);
//        return "house/detail";
//    }



}
