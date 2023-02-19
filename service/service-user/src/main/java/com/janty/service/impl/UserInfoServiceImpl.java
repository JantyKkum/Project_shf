package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.janty.dao.BaseDao;
import com.janty.dao.UserInfoDao;
import com.janty.entity.UserInfo;
import com.janty.service.Impl.BaseServiceImpl;
import com.janty.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/15 18:37
 * @description:
 */
@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    protected BaseDao<UserInfo> getEntityDao() {
        return userInfoDao;
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoDao.getUserInfoByPhone(phone);
    }
}
