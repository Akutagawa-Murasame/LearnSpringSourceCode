package org.mura.springframework.context;

import java.util.EventListener;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:08
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);
}
