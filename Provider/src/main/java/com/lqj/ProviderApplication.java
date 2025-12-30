package com.lqj;

import com.lqj.bean.URL;
import com.lqj.impl.HelloServerImpl;
import com.lqj.server.HttpServer;
import com.lqj.server.MapRemoteRegister;
import com.lqj.server.ServerRegister;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description
 */
public class ProviderApplication {
    public static void main(String[] args) {
        // 注册远程调用提供的服务
        ServerRegister.register(HelloServer.class.getName(), new HelloServerImpl());

        //注册服务url信息到注册中心
        MapRemoteRegister.register(HelloServer.class.getName(), new URL("localhost", 8080));

        //通过rpc框架， 启动 Netty / Tomcat 服务
        HttpServer httpServer = new HttpServer();
        httpServer.start("ldy", 8080);
    }
}
