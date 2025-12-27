package com.lqj.bean;

/**
 * @Author 李岐鉴
 * @Date 2025/12/27
 * @Description 跨服务传输的实体类
 */
public class Person {
    private String name;

    // 无参构造器：JSON反序列化必须
    public Person() {}

    // 有参构造器：用于快速创建对象
    public Person(String name) {
        this.name = name;
    }

    // getter/setter方法：保证字段的封装性与序列化兼容性
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

