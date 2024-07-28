package com.chandler.demo;

import com.chandler.demo.domain.UserModel;
import com.chandler.demo.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisApplicationTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void contextLoadsUtil() {
        redisUtils.set("test_utils_key", "test_utils_value");
        System.out.println(redisUtils.get("test_utils_key"));
    }

    @Test
    public void contextLoadHashUtil() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", new UserModel(1, "1", "code1"));
        map.put("2", new UserModel(2, "2", "code2"));
        redisUtils.setHash("hash", map);

        redisUtils.setHash("hash", "3", new UserModel(3,"3", "code3"));
        Map queryMap = redisUtils.getHash("hash");
        System.out.println(queryMap);
        System.out.println(queryMap.get("2"));
        System.out.println(queryMap.get("3"));
    }

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("test_key", "test_value");
        System.out.println(redisTemplate.opsForValue().get("test_key"));
    }

    @Test
    public void contextLoadsUser() {
        UserModel user = new UserModel(1, "1", "测试姓名");
        redisTemplate.opsForValue().set("test_user", user);
        System.out.println(redisTemplate.opsForValue().get("test_user"));
    }
}
