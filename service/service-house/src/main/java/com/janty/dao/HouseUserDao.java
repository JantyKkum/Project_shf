package com.janty.dao;

import com.janty.entity.HouseUser;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:06
 * @description:
 */
public interface HouseUserDao extends BaseDao<HouseUser>{

    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
