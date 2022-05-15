package org.mura.springframework.aop;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:22
 *
 * AOP切入点
 */
public interface Pointcut {
    /**
     * @return 这个切入点的ClassFilter
     */
    ClassFilter getClassFilter();

    /**
     * @return 这个切入点的MethodMatcher
     */
    MethodMatcher getMethodMatcher();
}
