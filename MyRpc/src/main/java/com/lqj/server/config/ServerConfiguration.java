package com.lqj.server.config;

import com.lqj.bean.URL;
import com.lqj.server.MapRemoteRegister;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 服务端配置类
 */
@Configuration
@ConditionalOnProperty(prefix = "lqj.rpc.server", name = "enable", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ServerProperty.class)
public class ServerConfiguration implements InitializingBean {


    @Resource
    private ServerProperty serverProperty;


    @Override
    public void afterPropertiesSet() throws Exception {
        MapRemoteRegister.register(serverProperty.getName(), new URL(serverProperty.getHostName(), serverProperty.getPort()));
    }


}
