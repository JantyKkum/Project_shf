package com.janty.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.janty.dao.BaseDao;
import com.janty.dao.DictDao;
import com.janty.dao.HouseDao;
import com.janty.entity.House;
import com.janty.service.HouseService;
import com.janty.service.Impl.BaseServiceImpl;
import com.janty.vo.HouseQueryVo;
import com.janty.vo.HouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 15:33
 * @description:
 */

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long houseId, Integer status) {
        House house = new House();
        house.setId(houseId);
        house.setStatus(status);
        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        PageHelper.startPage(pageNum, pageSize);
        Page<HouseVo> page = houseDao.findPageList(houseQueryVo);
        for (HouseVo houseVo : page) {
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            String houseFloorName = dictDao.getNameById(houseVo.getFloorId());
            String houseDirectionName = dictDao.getNameById(houseVo.getDirectionId());
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(houseFloorName);
            houseVo.setDirectionName(houseDirectionName);
        }
        return new PageInfo<>(page,5);
    }

    //重写此方法为了在详情中展示详细信息
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        if(house != null){
            String houseType = dictDao.getNameById(house.getHouseTypeId());
            String houseFloor = dictDao.getNameById(house.getFloorId());
            String houseDirection = dictDao.getNameById(house.getDirectionId());
            String houseBuildStructure = dictDao.getNameById(house.getBuildStructureId());
            String houseDecoration = dictDao.getNameById(house.getDecorationId());
            String houseUse = dictDao.getNameById(house.getHouseUseId());

            house.setHouseTypeName(houseType);
            house.setFloorName(houseFloor);
            house.setDirectionName(houseDirection);
            house.setBuildStructureName(houseBuildStructure);
            house.setDecorationName(houseDecoration);
            house.setHouseUseName(houseUse);
        }
        return house;
    }
}
