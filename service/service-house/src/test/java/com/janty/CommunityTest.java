package com.janty;

import com.github.pagehelper.Page;
import com.janty.dao.CommunityDao;
import com.janty.dao.DictDao;
import com.janty.entity.Community;
import com.janty.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 15:17
 * @description:
 */

@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class CommunityTest {


//    @Test
//    public void testFindListByParentId(){
//        Map<String, Object> filters = new HashMap<String, Object>();
//        Page<Community> page = communityDao.findPage(filters);
//        for (Community community : page) {
//            System.out.println("community = " + community);
//        }
//    }


}
