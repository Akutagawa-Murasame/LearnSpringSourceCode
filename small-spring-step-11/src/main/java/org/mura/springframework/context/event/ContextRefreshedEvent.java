package org.mura.springframework.context.event;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 15:17
 *
 * 上下文创建事件（容器初始化事件）
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    /**
     * 上下文创建事件原型构造（容器初始化事件）
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
