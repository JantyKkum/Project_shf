package com.janty.dao;

import com.janty.entity.Community;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 10:58
 * @description:
 */


public interface CommunityDao extends BaseDao<Community>{

    List<Community> findAll();


}
