package org.mura.springframework.context.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.mura.springframework.beans.factory.support.XmlBeanDefinitionReader;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:50
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String[] resources = getConfigLocations();

        if (null != resources) {
            xmlBeanDefinitionReader.loadBeanDefinitions(resources);
        }
    }

    protected abstract String[] getConfigLocations();
}