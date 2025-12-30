package com.lqj.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description ClientConfiguration 类
 */
@Configuration
@EnableConfigurationProperties(ClientProperty.class)
@ConfigurationProperties(prefix = "lqj.rpc.client")
public class ClientConfiguration {

    public ClientConfiguration(){
        System.out.println("ClientConfiguration被注册到ioc容器里了");
    }
}
