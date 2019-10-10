package com.wuhuafeng.redis.service;

import com.wuhuafeng.redis.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/8 20:52
 * @Version 1.0
 **/
@Service
public class HashService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    /**
     * 根据主键ID查询对象
     * @param id
     * @return
     */
    public User selectById(Integer id){

        HashOperations<String, Integer, User> hashOperations = redisTemplate.opsForHash();
        boolean b=false ;
        //1.先判断Redis中是否存在该KEy，如果存在，从Redis中取出，并返回
        try{
            //这里始终报异常，说类型转换错误，而需要的参数是Object。提示却是说要String类型。
             b = hashOperations.hasKey("user1",id);
        }catch (Exception e){
            e.printStackTrace();

        }


        if(b){
            User u = hashOperations.get("user1",id);

            System.out.println("Redis中查询出对象");
            return  u;
        }else{
            //如果不存在，从数据库中查询，取出赋值Redis，并返回
            User u = new User();
            u.setId(id);
            u.setAge(22);
            u.setName("华锋");
            u.setUsername("root");
            u.setPassword("root");
            System.out.println("从数据库中查询的对象");
            try{
                redisTemplate.opsForHash().put("user1",u.getId(),u);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("这里出问题了");
            }
            return u;
        }
    }
}
