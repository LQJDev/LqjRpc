package com.lqj.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 处理rpc请求中转类
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 方便扩展，这里不直接处理请求，而是调用请求处理类处理
        ServerHandler serverHandler = new ServerHandler();
        try {
            serverHandler.handle(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
