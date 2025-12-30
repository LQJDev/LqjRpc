package com.lqj.client.config;

import com.lqj.annotation.EnableRpcClients;
import com.lqj.annotation.RpcClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Map;
import java.util.Set;

/**
 * @Author 李岐鉴
 * @Date 2025/12/30
 * @Description RpcClientRegistrar 类
 */
public class RpcClientRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * @param importingClassMetadata 导入该注册器的类的注解元数据
     * @param registry               Bean定义注册器，用于向ioc容器注册Bean定义
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        //从注解@EnableRpcClients中获取包扫描路径
        Map<String, Object> enableRpcClientsAttrs =
                importingClassMetadata.getAnnotationAttributes(EnableRpcClients.class.getName());
        String[] basePackages = (String[]) enableRpcClientsAttrs.get("basePackages");

        //1.创建默认不过滤，且允许扫描接口的扫描器
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false) {
                    @Override
                    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                        return true;
                    }
                };


        //2.设置过滤器，只扫描带有RpcClient注解的类
        scanner.addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));

        //3.执行扫描
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                System.out.println(candidate.getBeanClassName());
                //4.注册Bean
                registerRpcClientBean(registry, candidate.getBeanClassName());
            }
        }
    }

    /**
     * @param registry  Bean定义注册器
     * @param className 要注册的全类名
     */
    private void registerRpcClientBean(BeanDefinitionRegistry registry, String className) {
        try {
            //获取Bean名称
            String beanName = getBeanName(className);

            //检查是否已经存在相同定义的Bean在ioc容器里
            if (registry.containsBeanDefinition(beanName)) {
                //避免重复注册
                return;
            }

            //给Bean定义构建器配置Bean工厂，然后注册到ioc容器里
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(RpcClientFactoryBean.class);

            //获取配置文件信息
            //先确保配置文件类已经注册
            if (!registry.containsBeanDefinition("clientProperty")) {
                BeanDefinitionBuilder configBuilder = BeanDefinitionBuilder
                        .genericBeanDefinition(ClientProperty.class);
                registry.registerBeanDefinition("clientProperty", configBuilder.getBeanDefinition());
            }


            //给RpcClientFacotryBean添加构造函数参数
            builder.addConstructorArgValue(Class.forName(className));
            //添加对ClientProperty的依赖引用
            builder.addPropertyReference("clientProperty", "clientProperty");
            //设置Bean的作用域
            builder.setScope("singleton");

            //注册Bean定义到容器里
            registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("无法加载类:" + className, e);
        }

    }

    /**
     * 从全类名中获取Bean名称（去除包名部分）
     *
     * @return
     */
    private String getBeanName(String className) {
        String res = className.substring(className.lastIndexOf('.') + 1);
        return res;
    }

}