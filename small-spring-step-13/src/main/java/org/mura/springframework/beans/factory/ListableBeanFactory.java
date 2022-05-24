package org.mura.springframework.beans.factory;

import org.mura.springframework.beans.BeansException;

import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:10
 */
public interface ListableBeanFactory extends BeanFactory {
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames() throws BeansException;
}
