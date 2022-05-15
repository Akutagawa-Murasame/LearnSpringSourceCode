package org.mura.springframework.context.support;

import org.mura.springframework.beans.BeansException;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:50
 *
 * 看到这个类名，真是有了种熟悉的感觉，tears down
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文（就是初始化上下文）
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文（就是初始化上下文）
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;

        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
