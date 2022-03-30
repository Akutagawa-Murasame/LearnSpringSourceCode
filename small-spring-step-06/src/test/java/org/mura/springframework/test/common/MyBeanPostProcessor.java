package org.mura.springframework.test.common;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanPostProcessor;
import org.mura.springframework.test.bean.UserService;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:52
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        初始化之前是指将bean对象注册之前，此时其实已经实例化
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置处理器");

        return bean;
    }
}
