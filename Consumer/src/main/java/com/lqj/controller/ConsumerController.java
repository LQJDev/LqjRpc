package com.lqj.controller;

import com.lqj.feign.HelloClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description 消费者接口
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private HelloClient helloClient;

    @GetMapping
    private String get() {
        return helloClient.hello("request head", "request body");
    }

}
