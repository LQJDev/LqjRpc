package com.lqj.client.config;

import cn.hutool.json.JSONUtil;
import com.lqj.annotation.RpcClient;
import com.lqj.balance.LoadBalance;
import com.lqj.bean.RpcRequest;
import com.lqj.bean.URL;
import com.lqj.client.HttpClient;
import com.lqj.server.MapRemoteRegister;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description RpcClientFactoryBean 类
 */
@Slf4j
@Data
public class RpcClientFactoryBean<T> implements FactoryBean {


    private Class<T> interfaceType;

    private ClientProperty clientProperty;

    public RpcClientFactoryBean(Class<T> interfaceType){
        this.interfaceType = interfaceType;
    }


    /**
     * 获取接口实例对象
     * @return
     * @throws Exception
     */
    @Nullable
    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceType},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //发起远程调用
                        RpcClient rpcClient = interfaceType.getAnnotation(RpcClient.class);
                        //获取服务实例名称
                        String serviceName = rpcClient.value();
                        //从注册中心获取该实例的URL信息
                        List<URL> urLs =
                                MapRemoteRegister.getURLs(serviceName);

                        URL url = LoadBalance.getRandom(urLs);




                        //获取要调用的接口路径和请求方式
                        RequestMapping annotation = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                        String path = annotation.value().length == 0 ? "" : annotation.value()[0];
                        RequestMethod requestMethod = annotation.method().length == 0 ? RequestMethod.POST : annotation.method()[0];

                        RpcRequest rpcRequest = new RpcRequest(path, requestMethod.name(), null, null);
                        //获取方法参数注解信息
                        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                        HashMap<String, Object> headers = new HashMap<>();
                        int i = 0;
                        for (Annotation[] parameterAnnotation : parameterAnnotations) {
                            boolean flag = true;
                            for (Annotation ann : parameterAnnotation) {
                                //请求体参数
                                if(ann instanceof RequestBody){
                                    rpcRequest.setBodyParameter(args[i]);
                                    flag = false;
                                }
                                //请求头参数
                                else if(ann instanceof RequestParam){
                                    String pname = ((RequestParam) ann).value();
                                    headers.put(pname, args[i]);
                                    flag = false;
                                }
                            }
                            //没有注解，则默认变量名作为key存放到请求头中
                            if(flag){
                                headers.put(method.getParameters()[i].getName(), args[i]);
                            }
                            i++;
                        }

                        rpcRequest.setQueryParameters(headers);

                        String jsonStr = null;

                        //请求失败的重试次数
                        Integer maxSize = clientProperty.getRetry().getMaxSize();
                        while(maxSize  > 0){
                            try{
                                //发起远程调用
                                jsonStr =  HttpClient.send(url.getHost(), url.getPort(), rpcRequest);
                                break;
                            }catch (Exception e){
                                log.error("远程调用{}第{}次请求失败", url.getHost() + ":" + url.getPort() ,clientProperty.getRetry().getMaxSize() - maxSize + 1);
//                                e.printStackTrace();
                            }
                            maxSize --;
                        }

                        if(maxSize == 0){
                            //到达最大上限次数
                            log.error("远程调用次数到达上限");
                        }

                        //json反序列化返回结果
                        if(method.getReturnType() == String.class){
                            return jsonStr;
                        }

                        return JSONUtil.toBean(jsonStr, method.getReturnType());
                    }
                }
        );
    }

    /**
     * 获取接口类型
     * @return
     */
    @Nullable
    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
