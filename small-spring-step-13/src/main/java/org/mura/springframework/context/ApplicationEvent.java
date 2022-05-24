package org.mura.springframework.context;

import java.util.EventObject;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 16:08
 *
 * 以继承 java.util.EventObject 定义出具备事件功能的抽象类 ApplicationEvent，后
 * 续所有事件的类都需要继承这个类
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * 事件原型构造
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
