package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.context.support.ClassPathXmlApplicationContext;
import org.mura.springframework.test.bean.UserService;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 23:06
 */
public class ApiTest {
    @Test
    public void test() throws BeansException {
//        1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

//        2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

        System.out.println(userService.getBeanFactory());
        System.out.println(userService.getApplicationContext());
    }

    @Test
    public void testProcessor() throws BeansException {
//        1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-processor.xml");
        applicationContext.registerShutdownHook();

//        2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

        System.out.println(userService.getBeanFactory());
        System.out.println(userService.getApplicationContext());
    }
}
