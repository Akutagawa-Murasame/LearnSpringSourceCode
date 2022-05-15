package org.mura.springframework.context.event;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.context.ApplicationEvent;
import org.mura.springframework.context.ApplicationListener;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 15:13
 *
 * 事件广播器
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加一个用来通知所有事件的监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除一个用来通知所有事件的监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 向合适的监听器广播应用事件
     */
    void multicastEvent(ApplicationEvent event) throws BeansException;
}
