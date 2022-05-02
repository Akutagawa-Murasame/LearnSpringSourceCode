package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.context.support.ClassPathXmlApplicationContext;
import org.mura.springframework.test.event.CustomEvent;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 23:06
 */
public class ApiTest {
    @Test
    public void test_event() throws BeansException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");

        applicationContext.publishEvent(new CustomEvent(applicationContext, 20220502L, "成功了"));
        applicationContext.registerShutdownHook();
    }
}