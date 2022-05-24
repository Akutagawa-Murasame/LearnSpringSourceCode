package org.mura.springframework.aop;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:18
 *
 * Advisor 承担了 Pointcut 和 Advice 的组合，Pointcut 用于获取 JoinPoint，而
 * Advice 决定于 JoinPoint 执行什么操作。
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * 获取启动这个切入点的Advisor
     */
    Pointcut getPointcut();
}
