package com.janty.service;

import com.janty.entity.HouseBroker;
import com.janty.entity.HouseImage;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:11
 * @description:
 */
public interface HouseImageService extends BaseService<HouseImage>{

    List<HouseImage> getHouseImageByHouseIdAndType(Long houseId, Integer type);
}
