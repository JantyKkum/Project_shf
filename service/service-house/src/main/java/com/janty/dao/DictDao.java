package com.janty.dao;

import com.janty.entity.Dict;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 9:50
 * @description:
 */
public interface DictDao {
    List<Dict> findListByParentId(Long id);

    Integer isParentNode(Long id);

    Dict findDictByDictCode(String dictCode);

    String getNameById(Long id);
}
