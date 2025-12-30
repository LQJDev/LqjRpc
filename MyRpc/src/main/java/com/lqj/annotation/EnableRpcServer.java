package com.lqj.annotation;

import java.lang.annotation.*;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 启用Rpc服务端配置功能
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface EnableRpcServer {
}
