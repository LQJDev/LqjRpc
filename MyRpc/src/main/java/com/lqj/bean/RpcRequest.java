package com.lqj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description RpcRequest 类
 */
@Data
@AllArgsConstructor
public class RpcRequest {

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private Map<String, Object> queryParameters;

    /**
     * 请求体
     */
    private Object bodyParameter;

}
