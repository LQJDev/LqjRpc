package com.lqj.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description 服务注册发现类
 */
public class ServerRegister {

    private static Map<String, Object> serverMap = new HashMap<>();

    /**
     * 注册服务
     *
     * @param serverName 服务名称
     * @param instance   服务实例
     */
    public static void register(String serverName, Object instance) {
        if (serverMap.containsKey(serverName)) {
            System.out.println(serverName + "存在相同名称的服务已注册");
            return;
        }
        serverMap.put(serverName, instance);
    }

    /**
     * 获取服务
     * @param serverName
     * @return
     */
    public static Object getServer(String serverName) {
        if (!serverMap.containsKey(serverName)) {
            System.out.println(serverName + "不存在");
            return null;
        }
        return serverMap.get(serverName);
    }
}
