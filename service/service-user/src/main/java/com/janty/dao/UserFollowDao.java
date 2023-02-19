package com.janty.dao;

import com.github.pagehelper.Page;
import com.janty.entity.UserFollow;
import com.janty.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 14:11
 * @description:
 */
public interface UserFollowDao extends BaseDao<UserFollow>{
    Integer getCountByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId")Long houseId);

    Page<UserFollowVo> findPageList(Long userId);
}
