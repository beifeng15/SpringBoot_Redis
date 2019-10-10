package com.wuhuafeng.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/8 11:44
 * @Version 1.0
 **/
@Service
public class StringService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //简单测试
    public void getString(String key) {
        System.out.println("缓存正在设置。。。。。。。。。");
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForValue().set("key2","value2");
        redisTemplate.opsForValue().set("key3","value3");
        redisTemplate.opsForValue().set("key4","value4");
        System.out.println("缓存已经设置完毕。。。。。。。");
        String result1=redisTemplate.opsForValue().get("key1").toString();
        String result2=redisTemplate.opsForValue().get("key2").toString();
        String result3=redisTemplate.opsForValue().get("key3").toString();
        System.out.println("缓存结果为：result："+result1+"  "+result2+"   "+result3);
    }

    //字符串过期测试
    public void setStringExpire() {
        redisTemplate.opsForValue().set("timeOutValue","timeOut",5,TimeUnit.SECONDS);
        String timeOutValue = redisTemplate.opsForValue().get("timeOutValue")+"";
        System.out.println("通过set(K key, V value, long timeout, TimeUnit unit)方法设置过期时间，过期之前获取的数据:"+timeOutValue);
        try {
            Thread.sleep(6*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timeOutValue = redisTemplate.opsForValue().get("timeOutValue")+"";
        System.out.print(",等待6s过后，获取的值:"+timeOutValue);
    }
}
