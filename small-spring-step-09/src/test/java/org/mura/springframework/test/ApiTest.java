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
    /**
     * 单例测试
     */
    @Test
    public void testSingleton() throws BeansException {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

// 2. 获取 Bean 对象调用方法
        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);

// 3. 配置 scope="prototype/singleton"，此处是原型
        System.out.println(userService01);
        System.out.println(userService02);

// 4. 打印十六进制哈希
        System.out.println(userService01 + " userService01十六进制哈希：" +
                Integer.toHexString(userService01.hashCode()));
        System.out.println(userService02 + " userService02十六进制哈希：" +
                Integer.toHexString(userService02.hashCode()));

        System.out.println();
    }

    /**
     * 测试代理增强
     */
    @Test
    public void test_factory_bean() throws BeansException {
// 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();
// 2. 调用代理方法
        UserService userService = applicationContext.getBean("userService", UserService.class);

        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}