package com.janty.dao;

import com.github.pagehelper.Page;
import com.janty.entity.House;
import com.janty.vo.HouseQueryVo;
import com.janty.vo.HouseVo;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 15:36
 * @description:
 */
public interface HouseDao extends BaseDao<House>{

    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
