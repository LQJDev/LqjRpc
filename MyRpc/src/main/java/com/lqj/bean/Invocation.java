package com.lqj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description Invocation 类
 */
@Data
@AllArgsConstructor
public class Invocation implements Serializable {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法参数类型数组
     */
    private Class[] parameterTypes;

    /**
     * 方法参数数组
     */
    private Object[] parameters;
}
