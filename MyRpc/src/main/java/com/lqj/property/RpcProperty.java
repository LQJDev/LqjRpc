package com.lqj.property;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description RpcProperty 类
 */
@Data
@AllArgsConstructor
public class RpcProperty {

    /**
     * 注册中心ip
     */
    public String hostName;

    /**
     * 注册中心端口
     */
    public Integer port;
}
