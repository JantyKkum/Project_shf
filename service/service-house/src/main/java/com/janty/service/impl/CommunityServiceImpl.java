package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.janty.dao.BaseDao;
import com.janty.dao.CommunityDao;
import com.janty.dao.DictDao;
import com.janty.entity.Community;
import com.janty.service.CommunityService;
import com.janty.service.Impl.BaseServiceImpl;
import com.janty.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 10:58
 * @description:
 */

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    //重写分页方法
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);

        Page<Community> page = communityDao.findPage(filters);
        List<Community> list = page.getResult();
        for (Community community : list) {
            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<Community>(page,10);
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        String areaName = dictDao.getNameById(community.getAreaId());
        String plateName = dictDao.getNameById(community.getPlateId());
        community.setAreaName(areaName);
        community.setPlateName(plateName);

        return community;
    }
}
