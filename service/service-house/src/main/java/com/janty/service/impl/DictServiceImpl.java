package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.DictDao;
import com.janty.entity.Dict;
import com.janty.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 9:49
 * @description:
 */

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        List<Dict> dictList = dictDao.findListByParentId(id);
        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Dict dict : dictList) {
            Map map = new HashMap();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            Integer count = dictDao.isParentNode(dict.getId());
            map.put("isParent",count > 0 ? true : false);
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.findDictByDictCode(dictCode);
        if(dict == null) return null;
        List<Dict> dicts = this.findListByParentId(dict.getId());
        return dicts;
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
