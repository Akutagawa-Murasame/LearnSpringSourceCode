package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValue;
import org.mura.springframework.beans.PropertyValues;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.BeanReference;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.mura.springframework.test.bean.UserDao;
import org.mura.springframework.test.bean.UserService;

/**
 * @author Akutagawa Murasame
 */
public class ApiTest {
    @Test
    public void testBeanFactory() throws BeansException {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        // 3. UserService 设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10003"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        // 4. UserService 注入 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 5. UserService 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
