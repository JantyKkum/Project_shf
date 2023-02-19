package com.janty.service;

import com.github.pagehelper.PageInfo;
import com.janty.entity.House;
import com.janty.vo.HouseQueryVo;
import com.janty.vo.HouseVo;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 15:30
 * @description:
 */
public interface HouseService extends BaseService<House> {
    void publish(Long houseId, Integer status);

    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
