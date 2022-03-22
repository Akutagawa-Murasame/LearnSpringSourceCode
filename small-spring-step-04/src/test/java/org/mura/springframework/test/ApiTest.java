package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.mura.springframework.test.bean.UserService;

/**
 * beanDefinition.getBeanClass().newInstance(); 实例化方式并没有考虑构造
 * 函数的入参，所以就这个坑就在这等着你了！那么我们的目标就很明显了，来
 * 把这个坑填平！
 *
 * 参考 Spring Bean 容器源码的实现方式，在 BeanFactory 中添加 Object
 * getBean(String name, Object... args) 接口，这样就可以在获取
 * Bean 时把构造函数的入参信息传递进去了
 *
 * 另外一个核心的内容是使用什么方式来创建含有构造函数的 Bean 对象呢？这里有
 * 两种方式可以选择，一个是基于 Java 本身自带的方法
 * DeclaredConstructor，另外一个是使用 Cglib 来动态创建 Bean 对象。
 * Cglib 是基于字节码框架 ASM 实现，所以你也可以直接通过 ASM 操作指令码来
 * 创建对象
 */
public class ApiTest {
    @Test
    public void testBeanFactory() throws BeansException {
//        1、初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

//        2、注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        beanFactory.registerBeanDefinition("defaultUserService", beanDefinition);

//        3、获取有参bean
        UserService userService = (UserService) beanFactory.getBean("userService", "名称");

//        4、获取无参bean
        UserService defaultUserService = (UserService) beanFactory.getBean("defaultUserService");

        userService.queryUserInfo();
        defaultUserService.queryUserInfo();

        System.out.println(userService == defaultUserService);
    }
}
