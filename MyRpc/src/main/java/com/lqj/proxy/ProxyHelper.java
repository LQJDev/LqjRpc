package com.lqj.proxy;

import cn.hutool.json.JSONUtil;
import com.lqj.balance.LoadBalance;
import com.lqj.bean.Invocation;
import com.lqj.bean.URL;
import com.lqj.client.CallBackRegister;
import com.lqj.client.HttpClient;
import com.lqj.server.MapRemoteRegister;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author 李岐鉴
 * @Date 2025/12/28
 * @Description 获取代理对象的通用方法
 */
@Slf4j
public class ProxyHelper {


    public static <T> T getProxyInstance(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 生成请求调用类
                        Invocation invocation = new Invocation(interfaceClass.getName(),
                                method.getName(), method.getParameterTypes(), args
                        );

                        // 服务发现
                        List<URL> urLs = MapRemoteRegister.getURLs(interfaceClass.getName());

                        // json字符串，存放远程调用返回的结果
                        String jsonStr = null;

                        // 重试次数
                        int maxCnt = 3;

                        while (maxCnt > 0) {

                            // 负载均衡
                            URL url = LoadBalance.getRandom(urLs);
                            try {
                                jsonStr = HttpClient.send(url.getHost(), url.getPort(), invocation);
                                break;
                            } catch (Exception e) {
                                log.info("第{}次--服务器{}:{}的{}类的{}方法调用异常", (4-maxCnt), url.getHost(), url.getPort(), interfaceClass.getName(), method.getName());
                                System.out.println(url.getHost() + ":" + url.getPort() + ",远程调用异常" + (4 - maxCnt));
                            }
                            maxCnt--;
                            if (maxCnt == 0) {
                                // 降级处理
                                Object callBack = CallBackRegister.getCallBack(interfaceClass.getName());
                                // 如果降级处理类不为空，则调用
                                if (callBack != null) {
                                    return method.invoke(callBack, args);
                                }
                            }

                        }

                        if(jsonStr == null){
                            System.out.println("远程调用失败，达到最大重试次数，且无降级处理类");
                        }

                        Class<?> returnType = method.getReturnType();
                        //如果返回值类型就是字符串，则无需进行json反序列化
                        if(returnType == String.class){
                            return jsonStr;
                        }
                        Object res = JSONUtil.toBean(jsonStr, returnType);
                        return res;
                    }
                });

    }

}


