package com.wuhuafeng.redis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/8 20:07
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class StringTest {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    public void testString(){


    }
}
