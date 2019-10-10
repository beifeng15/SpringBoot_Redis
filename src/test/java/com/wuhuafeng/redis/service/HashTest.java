package com.wuhuafeng.redis.service;

import com.wuhuafeng.redis.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/8 20:55
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class HashTest {
    @Autowired
    private HashService hashService;
    @Test
    public void testHash(){
        int id =5;
        User user = hashService.selectById(id);
        System.out.println(user.toString());
    }
}
