package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.janty.entity.Community;
import com.janty.entity.Dict;
import com.janty.service.CommunityService;
import com.janty.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 11:04
 * @description:
 */

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController{

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @RequestMapping
    public String findPage(Map map, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        PageInfo<Community> pageInfo = communityService.findPage(filters);

        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);
        map.put("page",pageInfo);
        map.put("filters",filters);

        return  "community/index";
    }

    @RequestMapping("/create")
    public String goAddPage(Map map){
        //获取北京
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);
        return "community/create";
    }

    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id")Long id,Map map){
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);

        Community community = communityService.getById(id);
        map.put("community",community);
        return "community/edit";
    }

    @RequestMapping("/update")
    public String update(Community community){
        communityService.update(community);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        communityService.delete(id);
        return "redirect:/community";
    }

}
