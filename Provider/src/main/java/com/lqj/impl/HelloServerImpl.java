package com.lqj.impl;

import com.lqj.HelloServer;
import com.lqj.bean.Person;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description HelloServer实现类
 */
public class HelloServerImpl implements HelloServer {
    @Override
    public Person sayHello(String msg) {
        System.out.println("hello " +  msg + ", this is provider");
        return new Person("lqj");
    }
}
