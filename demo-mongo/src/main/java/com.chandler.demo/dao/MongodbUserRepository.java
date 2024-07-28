package com.chandler.demo.dao;

/**
 * ClassName: MongodbUserRepository
 * Package: com.chandler.demo.dao
 * Description:
 *
 * @Author: w.yi
 * @Create: 2024/7/28-9:18
 */

import com.chandler.demo.domain.MongodbUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @version 1.0
 * @date 2023/7/13 16:46
 */
public interface  MongodbUserRepository extends MongoRepository<MongodbUser, Long> {
    //根据用户名查询
    MongodbUser findByUsername(String username);
}

