package com.janty.service;

import com.github.pagehelper.PageInfo;
import com.janty.entity.UserFollow;
import com.janty.vo.UserFollowVo;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 14:10
 * @description:
 */
public interface UserFollowService extends BaseService<UserFollow>{

    void follow(Long id, Long houseId);

    //查询是否关注
    Boolean isFollowed(Long id, Long id1);

    //分页查询我的关注
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id);

    void cancelFollow(Long id);
}
