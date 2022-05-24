package org.mura.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:17
 *
 * 在 Spring 框架中，Advice 都是通过方法拦截器 MethodInterceptor 实现的。环
 * 绕 Advice 类似一个拦截器的链路，BeforeAdvice、AfterAdvice 等，不过暂时我
 * 们需要那么多就只定义了一个 MethodBeforeAdvice 的接口定义。
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 一个方法被调用之前的回调函数
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
