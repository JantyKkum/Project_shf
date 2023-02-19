package com.janty.service;

import com.janty.entity.UserInfo;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/15 18:39
 * @description:
 */
public interface UserInfoService extends BaseService<UserInfo>{
    UserInfo getUserInfoByPhone(String phone);
}
