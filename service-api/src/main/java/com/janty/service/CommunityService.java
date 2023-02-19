package com.janty.service;

import com.janty.entity.Community;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 11:00
 * @description:
 */
public interface CommunityService extends BaseService<Community>{

    List<Community> findAll();
}
