package com.lqj.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 服务端配置信息
 */
@ConfigurationProperties(prefix = "lqj.rpc.server")
@Data
public class ServerProperty {


    /**
     * 服务名称
     */
    private String name;

    /**
     * 服务ip
     */
    private String hostName = "127.0.0.1";

    /**
     * 服务端口
     */
    private Integer port = 8080;
}
