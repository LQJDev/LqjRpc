package com.lqj.balance;

import com.lqj.bean.URL;

import java.util.List;
import java.util.Random;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description 负载均衡实现类
 */
public class LoadBalance {

    public static URL getRandom(List<URL> urls) {
        Random random = new Random();
        int target = random.nextInt(urls.size());
        return urls.get(target);
    }
}
