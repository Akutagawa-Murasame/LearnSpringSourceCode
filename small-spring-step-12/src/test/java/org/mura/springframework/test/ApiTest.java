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
    @Test
    public void test_aop() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}