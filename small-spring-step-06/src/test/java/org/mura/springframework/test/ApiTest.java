package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.mura.springframework.beans.factory.support.XmlBeanDefinitionReader;
import org.mura.springframework.context.support.ClassPathXmlApplicationContext;
import org.mura.springframework.test.bean.UserService;
import org.mura.springframework.test.common.MyBeanFactoryPostProcessor;
import org.mura.springframework.test.common.MyBeanPostProcessor;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 23:06
 */
public class ApiTest {
    /**
     * 不在xml中注册beanProcessor，手动初始化
     */
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() throws BeansException {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean实例化之后，修改 Bean 属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // 5. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

//        打印结果有两个后置处理器是因为我们注册了两个bean（userDao和userService）
    }

    /**
     * 在xml中初始化beanProcessor
     */
    @Test
    public void test_xml() throws BeansException {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-processor.xml");

        // 2. 获取Bean对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

//        打印结果有两个后置处理器是因为我们注册了两个bean（userDao和userService）
    }

    @Test
    public void test() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-processor.xml");
    }
}