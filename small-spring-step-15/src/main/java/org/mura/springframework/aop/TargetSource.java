package org.mura.springframework.aop;

import org.mura.springframework.utils.ClassUtils;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:22
 *
 * 装载了被代理类的信息
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取被代理类实现的接口
     *
     * 这个 target 可能是 JDK 代理 创建也可能是 CGlib 创建，为了保证都能正确的
     * 获取到结果，这里需要增加判断
     */
    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();

        if (ClassUtils.isCglibProxyClass(clazz)) {
            clazz = clazz.getSuperclass();
        }

        return clazz.getInterfaces();
    }

    /**
     * 获取被代理类
     */
    public Object getTarget(){
        return this.target;
    }
}