package org.mura.springframework.test.event;

import org.mura.springframework.context.ApplicationListener;
import org.mura.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:16
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
        System.out.println("收到：" + event.getSource());
    }
}
