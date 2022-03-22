package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.BeanDefinition;
import org.mura.springframework.BeanFactory;
import org.mura.springframework.test.bean.UserService;

public class ApiTest {
    @Test
    public void test_BeanFactory() {
//        1、初始化BeanFactory
        BeanFactory beanFactory = new BeanFactory();

//        2、注册bean
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);

//        3、获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

//        - 在单测中主要包括初始化 Bean 工厂、注册 Bean、获取 Bean，三个步骤，使用
//        效果上贴近于 Spring，但显得会更简化。
//        - 在 Bean 的注册中，这里是直接把 UserService 实例化后作为入参传递给
//        BeanDefinition 的，在后续的陆续实现中，我们会把这部分内容放入 Bean 工厂中
//        实现。
    }
}