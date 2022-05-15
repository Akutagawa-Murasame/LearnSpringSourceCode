package org.mura.springframework.test.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.mura.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:26
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法：" + method.getName());
    }
}
