package com.lqj.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description 服务降级注册类
 */
public class CallBackRegister {

    // 降级处理类集合
    private static Map<String, Object> callBackMap = new HashMap<>();

    /**
     * 注册降级处理类
     * @param interfaceName
     * @param instance
     */
    public static void register(String interfaceName, Object instance) {
        callBackMap.put(interfaceName, instance);
    }

    /**
     * 获取降级处理类
     * @param interfaceName
     * @return
     */
    public static Object getCallBack(String interfaceName) {
        return callBackMap.get(interfaceName);
    }
}
