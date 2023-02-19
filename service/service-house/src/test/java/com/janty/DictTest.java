package com.janty;

import com.janty.dao.DictDao;
import com.janty.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/13 15:08
 * @description:
 */

@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class DictTest {

    @Autowired
    private DictDao dictDao;

    @Test
    public void testFindListByParentId(){
        List<Dict> listByParentId = dictDao.findListByParentId(1l);
        for (Dict dict : listByParentId) {
            System.out.println("dict = " + dict);
        }
    }



}
