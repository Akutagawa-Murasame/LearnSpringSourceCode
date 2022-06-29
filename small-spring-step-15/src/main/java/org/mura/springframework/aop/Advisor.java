package org.mura.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:17
 *
 * 返回此方面的Advice部分。一个Advice可以是一个Interceptor、一个之前的Advice、一个抛出的Advice等。
 */
public interface Advisor {
    Advice getAdvice();
}