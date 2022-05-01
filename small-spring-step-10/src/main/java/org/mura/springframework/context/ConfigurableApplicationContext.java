package org.mura.springframework.context;

import org.mura.springframework.beans.BeansException;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:51
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器（初始化容器）
     * refresh()方法是spring的核心，在其中完成了容器的初始化
     */
    void refresh() throws BeansException;

    /**
     * 虚拟机关闭钩子注册调用销毁方法
     */
    void registerShutdownHook();

    /**
     * 关闭容器
     */
    void close() throws BeansException;
}
