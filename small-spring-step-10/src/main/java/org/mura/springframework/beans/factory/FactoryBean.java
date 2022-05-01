package org.mura.springframework.beans.factory;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/24 21:51
 *
 * 保存FactoryBean的信息
 */
public interface FactoryBean<T> {
    /**
     * 获取对象
     */
    T getObject() throws Exception;

    /**
     * 获取对象类型
     */
    Class<T> getObjectType();

    /**
     * 判断是否单例
     */
    boolean isSingleton();
}
