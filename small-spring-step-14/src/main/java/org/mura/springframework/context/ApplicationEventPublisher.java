package org.mura.springframework.context;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:08
 *
 * 事件发布者，所有的事件都需要从这个接口发布出去
 */
public interface ApplicationEventPublisher {
    /**
     * 通知在某个应用注册的所有监听器
     */
    void publishEvent(ApplicationEvent event);
}
