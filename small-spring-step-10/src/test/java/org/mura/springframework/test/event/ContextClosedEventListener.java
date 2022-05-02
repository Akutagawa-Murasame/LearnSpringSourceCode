package org.mura.springframework.test.event;

import org.mura.springframework.context.ApplicationListener;
import org.mura.springframework.context.event.ContextClosedEvent;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:16
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
        System.out.println("收到：" + event.getSource());
    }
}
