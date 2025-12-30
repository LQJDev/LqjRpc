package com.lqj.controller;

import com.lqj.annotation.RpcService;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description HelloController 类
 */
@RpcService()
@RestController
@RequestMapping("/hello")
public class HelloController {
    @PostMapping
    public String hello(@RequestParam("msg")String msg, @RequestBody String body){
        System.out.println(msg + "\n" + body);
        return "你好，我是Provider,我收到你的消息了" + msg;
    }
}
