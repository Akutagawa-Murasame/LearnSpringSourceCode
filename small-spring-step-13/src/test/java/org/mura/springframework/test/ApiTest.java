package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.context.support.ClassPathXmlApplicationContext;
import org.mura.springframework.test.bean.IUserService;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 23:06
 */
public class ApiTest {
    /**
     * 测试占位符
     */
    @Test
    public void test_placeHolder() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
    }

    /**
     * 测试包扫描
     */
    @Test
    public void test_scan() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}