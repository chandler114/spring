package com.chandler.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ClassName: MongodbUser
 * Package: com.chandler.demo.domain
 * Description:
 *
 * @Author: w.yi
 * @Create: 2024/7/28-9:12
 */
@Data
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
@Document(collection = "user")//Document注解表明对应了MongoDB中的user表。
public class MongodbUser {

    @Id
    private Long id;
    private String username;
    private Integer age;
}

