package com.lqj.client.config;

import lombok.Data;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 客户端配置信息
 */
@Data
public class ClientProperty {

    public ClientProperty(){
        System.out.println("ClientProperty被注册到ioc了");
    }

    private Retry retry;

    @Data
    public static class Retry{
        /**
         * 最大重试次数
         */
        private Integer maxSize = 3;
    }

}
