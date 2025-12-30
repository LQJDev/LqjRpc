package com.lqj.callback;

import com.lqj.HelloServer;
import com.lqj.bean.Person;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description HelloServer的降级处理类
 */
public class HelloServerCallBack implements HelloServer {
    @Override
    public Person sayHello(String msg) {
        // 降级逻辑：返回默认Person对象，避免服务雪崩
        System.out.println("服务调用失败，执行降级逻辑");
        return new Person("down");
    }
}
