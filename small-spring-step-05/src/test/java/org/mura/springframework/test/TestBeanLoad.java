package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.mura.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.mura.springframework.test.bean.UserService;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/26 0:13
 */
public class TestBeanLoad {
    @Test
    public void test() throws BeansException {
//         1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

//         2. 读取配置文件&注册 Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

//         3. 获取 Bean 对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();

        System.out.println("测试结果：" + result);

//        有点意思
    }

}
