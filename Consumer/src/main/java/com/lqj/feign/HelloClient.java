package com.lqj.feign;

import com.lqj.annotation.RpcClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description Hello远程调用接口
 */
@RpcClient("provider")
public interface HelloClient {

    @PostMapping("/hello")
    public String hello(@RequestParam("msg")String msg, @RequestBody String body);

}
