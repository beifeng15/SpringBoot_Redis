package com.wuhuafeng.redis.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/8 11:33
 * @Version 1.0
 **/
@Data
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = -4453836023805270065L;

    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;

    public static String getKeyName(){
        return "user:";
    }
    public static String getName(){
        return "user";
    }
}
