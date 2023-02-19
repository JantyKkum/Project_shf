package com.janty.service;

import com.janty.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 9:49
 * @description:
 */


public interface DictService {
    List<Map<String, Object>> findZnodes(Long id);

    List<Dict> findListByParentId(Long parentId);

    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long id);
}
