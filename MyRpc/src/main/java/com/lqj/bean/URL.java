package com.lqj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description URL 类
 */
@Data
@AllArgsConstructor
public class URL implements Serializable {

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;
}
