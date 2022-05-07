package org.mura.springframework.context.event;

import org.mura.springframework.context.ApplicationContext;
import org.mura.springframework.context.ApplicationEvent;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 15:13
 *
 * 关于应用上下文的事件
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * 应用上下文事件构造
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取应用上下文事件发生对象（返回一个应用上下文，这个上下文导致了事件的发生）
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
