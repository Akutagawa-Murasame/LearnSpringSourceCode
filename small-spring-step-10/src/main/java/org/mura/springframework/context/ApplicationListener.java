package org.mura.springframework.context;

import java.util.EventListener;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:08
 *
 * 自定义的监听器只需要继承这个接口，并且传入自定义事件
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * Handle an application event.
     * @param event the event to respond to
     * E是自定义的事件类型
     */
    void onApplicationEvent(E event);
}
