package org.mura.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:17
 *
 * Spring 的 AOP 把 Advice 细化了 BeforeAdvice、AfterAdvice、
 * AfterReturningAdvice、ThrowsAdvice，目前我们做的测试案例中只用到了
 * BeforeAdvice
 */
public interface BeforeAdvice extends Advice {

}