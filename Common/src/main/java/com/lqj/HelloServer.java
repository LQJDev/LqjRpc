package com.lqj;

import com.lqj.bean.Person;

/**
 * @Author 李岐鉴
 * @Date 2025/12/27
 * @Description 远程调用接口
 */
public interface HelloServer {

    /**
     * 服务核心方法：接收字符串参数，返回Person对象
     * @param msg 客户端传入的消息
     * @return 自定义实体类Person
     */
    public Person sayHello(String msg);
}
