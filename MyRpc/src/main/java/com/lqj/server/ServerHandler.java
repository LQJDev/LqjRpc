package com.lqj.server;

import cn.hutool.json.JSONUtil;
import com.lqj.bean.Invocation;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description 请求处理实现类
 */
public class ServerHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Invocation invocation = (Invocation) new ObjectInputStream(request.getInputStream()).readObject();

        // 通过接口名称，获取调用类的实例
        Object instance = ServerRegister.getServer(invocation.getInterfaceName());
        Method method = instance.getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        method.setAccessible(true);
        Object result = method.invoke(instance, invocation.getParameters());

        // 将json序列化的结果写入到输出流中
        try (ServletOutputStream sos = response.getOutputStream()) {
            ObjectOutputStream oos = new ObjectOutputStream(sos);
            String jsonStr = JSONUtil.toJsonStr(result);
            oos.writeObject(jsonStr);
        }

    }
}
