package com.chandler.demo;

import com.chandler.demo.dao.MongodbUserRepository;
import com.chandler.demo.domain.MongodbUser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName: MongoApplicationTest
 * Package: com.chandler.demo
 * Description:
 *
 * @Author: w.yi
 * @Create: 2024/7/28-9:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class MongoApplicationTest {

    @Resource
    MongodbUserRepository mongodbUserRepository;

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    public void insertTest(){
        mongodbUserRepository.deleteAll();

        // 创建三个User，并验证User总数
        mongodbUserRepository.save(new MongodbUser(1L, "赵云", 30));
        mongodbUserRepository.save(new MongodbUser(2L, "貂蝉", 40));
        mongodbUserRepository.save(new MongodbUser(3L, "李白", 50));
        Assertions.assertEquals(3, mongodbUserRepository.findAll().size());
    }

    //根据年龄范围查询
    @Test
    public void queryByNameTest() {
        //范围查询
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gte(30).lte(60));
        System.out.println(mongoTemplate.find(query, MongodbUser.class));
    }

}
