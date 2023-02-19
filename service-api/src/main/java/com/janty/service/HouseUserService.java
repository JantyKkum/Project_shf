package com.janty.service;

import com.janty.entity.HouseImage;
import com.janty.entity.HouseUser;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:11
 * @description:
 */
public interface HouseUserService extends BaseService<HouseUser>{

    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
