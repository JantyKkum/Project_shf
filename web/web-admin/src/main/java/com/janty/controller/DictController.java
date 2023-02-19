package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.Dict;
import com.janty.result.Result;
import com.janty.service.DictService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 9:44
 * @description:
 */

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController{

    @Reference
    private DictService dictService;

    @RequestMapping
    public String toIndex(){
        return "dict/index";
    }

    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0")Long id){

        List<Map<String ,Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    @RequestMapping("/findListByParentId/{areaId}")
    @ResponseBody
    public Result findListByParentId(@PathVariable("areaId")Long areaId){
        List<Dict> list = dictService.findListByParentId(areaId);
        return Result.ok(list);
    }

}
