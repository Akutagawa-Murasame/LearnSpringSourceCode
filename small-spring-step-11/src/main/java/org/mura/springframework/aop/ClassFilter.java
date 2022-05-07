package org.mura.springframework.aop;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:21
 *
 * 类过滤器，实现这个接口以用来判断其他类是否满足要求
 */
public interface ClassFilter {
    /**
     * 判断切入点是否应该应用到给定的interface或者class
     * @param clazz 待判断的class
     * @return 是否可以应用到目标class
     */
    boolean matches(Class<?> clazz);
}
