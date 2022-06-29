package org.mura.springframework.beans.factory;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/20 18:39
 *
 * 实现此接口以感知到所属的 ClassLoader
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}