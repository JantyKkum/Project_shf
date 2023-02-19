package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.BaseDao;
import com.janty.dao.HouseImageDao;
import com.janty.entity.HouseImage;
import com.janty.service.HouseImageService;
import com.janty.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:18
 * @description:
 */

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService{

    @Autowired
    private HouseImageDao houseImageDao;

    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }

    @Override
    public List<HouseImage> getHouseImageByHouseIdAndType(Long houseId, Integer type) {
        return houseImageDao.getHouseImageByHouseIdAndType(houseId,type);
    }
}
