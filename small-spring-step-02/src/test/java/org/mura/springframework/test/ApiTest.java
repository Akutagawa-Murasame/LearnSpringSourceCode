package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.mura.springframework.test.bean.UserService;

/**
 * - 在此次的单元测试中除了包括；Bean 工厂、注册 Bean、获取 Bean，三个步骤，
 * 还额外增加了一次对象的获取和调用。这里主要测试验证单例对象的是否正确的存
 * 放到了缓存中。
 * - 此外与上一章节测试过程中不同的是，我们把 UserService.class 传递给了
 * BeanDefinition 而不是像上一章节那样直接 new UserService() 操作。
 *
 * 现在的坑就变成了我们只能实例化默认构造函数
 */
public class ApiTest {
    @Test
    public void testBeanFactory() throws BeansException {
//        1、初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

//        2、注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

//        3、第一次获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

//        4、第二次获取 bean from singleton
        UserService userServiceSingleton = (UserService) beanFactory.getBean("userService");

        userServiceSingleton.queryUserInfo();

//        true，因为是单例的
        System.out.println(userService == userServiceSingleton);
    }

//    就这几个类，就要绕晕了
}
