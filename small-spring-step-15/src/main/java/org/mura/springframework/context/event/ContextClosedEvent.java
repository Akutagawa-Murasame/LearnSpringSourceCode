package org.mura.springframework.context.event;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 15:16
 *
 * 上下文关闭事件
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * 上下文关闭事件原型构造
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
