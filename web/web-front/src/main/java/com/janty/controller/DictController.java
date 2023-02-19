package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.Dict;
import com.janty.result.Result;
import com.janty.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/15 16:25
 * @description:
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    DictService dictService;

    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode){
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId){
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }
}
