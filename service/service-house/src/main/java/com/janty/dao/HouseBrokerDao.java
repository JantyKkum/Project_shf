package com.janty.dao;

import com.janty.entity.HouseBroker;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 9:53
 * @description:
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker>{

    //根据房子id查询经纪人
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
