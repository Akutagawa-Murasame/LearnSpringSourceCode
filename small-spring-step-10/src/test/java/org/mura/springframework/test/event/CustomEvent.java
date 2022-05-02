package org.mura.springframework.test.event;

import org.mura.springframework.context.event.ApplicationContextEvent;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:16
 *
 * 监听器也是一种设计模式，叫做监视者模式
 */
public class CustomEvent extends ApplicationContextEvent {
    private Long id;
    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}