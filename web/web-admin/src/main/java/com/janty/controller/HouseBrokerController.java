package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.Admin;
import com.janty.entity.HouseBroker;
import com.janty.service.AdminService;
import com.janty.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 11:25
 * @description:
 */

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController{

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map){
        map.put("houseId",houseId);
        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);

        return "houseBroker/create";
    }

    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        map.put("houseBroker",houseBroker);
        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);
        return "houseBroker/edit";
    }

    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("brokerId") Long brokerId){
        houseBrokerService.delete(brokerId);
        return "redirect:/house/" + houseId;
    }
}
