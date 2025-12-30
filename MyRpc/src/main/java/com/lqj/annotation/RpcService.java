package com.lqj.annotation;

import java.lang.annotation.*;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description rpc服务端注册服务接口
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcService {
    /**
     * 服务版本号，默认1.0
     */
    String version() default "1.0";
}
