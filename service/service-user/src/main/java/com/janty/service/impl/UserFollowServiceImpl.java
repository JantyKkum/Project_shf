package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.janty.dao.BaseDao;
import com.janty.dao.UserFollowDao;
import com.janty.entity.UserFollow;
import com.janty.service.DictService;
import com.janty.service.Impl.BaseServiceImpl;
import com.janty.service.UserFollowService;
import com.janty.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/16 14:10
 * @description:
 */
@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long id, Long houseId) {
        //创建userFollow对象
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer count = userFollowDao.getCountByUserIdAndHouseId(userId,houseId);
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum,pageSize);
        //调用UserFollowDao
        Page<UserFollowVo> page = userFollowDao.findPageList(userId);
        for (UserFollowVo userFollowVo : page) {
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page,5);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }
}
