package com.janty.dao;

import com.janty.entity.UserInfo;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/15 18:36
 * @description:
 */
public interface UserInfoDao extends BaseDao<UserInfo>{
    UserInfo getUserInfoByPhone(String phone);
}
