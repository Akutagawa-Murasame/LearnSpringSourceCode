package org.mura.springframework.utils;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:15
 */
public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;

        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable e) {
//            jdk关于getContextClassLoader说获取失败说明是根加载器加载的
//            无法访问线程上下文 ClassLoader - 回退到系统类加载器...
        }

//        jdk关于getContextClassLoader的注释说返回null说明是系统类加载器加载的
//        这两种情况我们统一使用这个类的加载器，保证一定能找到类
        if (classLoader == null) {
            classLoader = ClassUtils.class.getClassLoader();
        }

        return classLoader;
    }
}
