package com.wuhuafeng.redis.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuhuafeng.redis.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/10 11:10
 * @Version 1.0
 **/
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;

    @RequestMapping("/user/save")
    public void saveUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("beifeng");
        user.setPassword("root");
        user.setName("锋哥");
        //需要注意：普通的连接对象没有办法吧Java对象直接存入到Redis，需要我们进行对象的序列化，然后使用Redis进行存储，而下面取回序列化的内容后，在通过转换变为Java对象。
        redisTemplate.opsForValue().set("beifeng", JSONObject.toJSONString(user)); //使用的是fastJSON
    }

    @RequestMapping("/user/get")
    public String getUser() {
        return (String)redisTemplate.opsForValue().get("beifeng");
    }
}
