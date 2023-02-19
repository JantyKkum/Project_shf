package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.BaseDao;
import com.janty.dao.HouseBrokerDao;
import com.janty.entity.HouseBroker;
import com.janty.service.BaseService;
import com.janty.service.HouseBrokerService;
import com.janty.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:15
 * @description:
 */

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerDao houseBrokerDao;

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }

    @Override
    public List<HouseBroker> getHouseBrokerByHouseId(Long houseId) {
        return houseBrokerDao.getHouseBrokerByHouseId(houseId);
    }
}
