package com.janty.dao;

import com.janty.entity.HouseImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 9:58
 * @description:
 */

public interface HouseImageDao extends BaseDao<HouseImage>{
    List<HouseImage> getHouseImageByHouseIdAndType(@Param("houseId") Long houseId, @Param("type") Integer type);
}
