package com.lqj.annotation;

import com.lqj.client.config.RpcClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description 启用Rpc客户端配置功能注解, 将Rpc远程调用的接口的代理实现类注册到ioc容器里
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RpcClientRegistrar.class})
public @interface EnableRpcClients {

    /**
     * 要扫描的包路径，即注册到ioc容器的client类的包路径
     */
    String[] basePackages();

}
