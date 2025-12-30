package com.lqj.server;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description HttpServer 类
 */
public class HttpServer {


    public void start(String hostName, Integer port) {
        if (null == hostName || hostName.trim().isEmpty()) {
            throw new IllegalArgumentException("主机名不能为空");
        }

        if (null == port) {
            throw new IllegalArgumentException("端口号不能为空");
        }

        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("端口号必须在0-65535之间");
        }

        // 创建Tomcat实例，这是嵌入式Tomcat的入口
        Tomcat tomcat = new Tomcat();

        // 获取Tomcat的Server对象（Tomcat的顶层核心组件，管理整个服务生命周期）
        Server server = tomcat.getServer();
        // 获取Tomcat的Service对象（Tomcat的顶层核心组件，管理多个Connector）
        Service service = server.findService("Tomcat");

        // 创建Connector对象，并设置其属性
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

        connector.setPort(port);

        StandardEngine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        StandardHost standardHost = new StandardHost();
        standardHost.setName(hostName);

        String contextPath = "";
        StandardContext context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        standardHost.addChild(context);
        engine.addChild(standardHost);
        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "dispatcherServlet", new DispatcherServlet());

        context.addServletMappingDecoded("/*", "dispatcherServlet");

        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
