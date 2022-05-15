package org.mura.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:22
 *
 * 方法匹配器，实现这个接口以判断其他类的方法是否符合要求
 */
public interface MethodMatcher {
    /**
     * 找到目标类中是否有切面表达式匹配的方法
     */
    boolean matches(Method method, Class<?> targetClass);
}
