package org.mura.springframework.context.event;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.BeanFactory;
import org.mura.springframework.context.ApplicationEvent;
import org.mura.springframework.context.ApplicationListener;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 15:17
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) throws BeansException {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(final ApplicationEvent event) throws BeansException {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
//            监听事件
            listener.onApplicationEvent(event);
        }
    }
}
