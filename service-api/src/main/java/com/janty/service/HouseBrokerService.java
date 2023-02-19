package com.janty.service;

import com.janty.entity.HouseBroker;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:11
 * @description:
 */
public interface HouseBrokerService extends BaseService<HouseBroker>{

    //根据房子id查询经纪人
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
