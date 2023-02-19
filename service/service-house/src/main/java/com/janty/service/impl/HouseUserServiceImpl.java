package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.BaseDao;
import com.janty.dao.HouseUserDao;
import com.janty.entity.HouseUser;
import com.janty.service.HouseUserService;
import com.janty.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 10:21
 * @description:
 */

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService{

    @Autowired
    private HouseUserDao houseUserDao;

    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }

    @Override
    public List<HouseUser> getHouseUserByHouseId(Long houseId) {
        return houseUserDao.getHouseUserByHouseId(houseId);
    }
}
