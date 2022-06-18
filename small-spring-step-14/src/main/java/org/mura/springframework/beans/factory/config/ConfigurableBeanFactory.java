package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.HierarchicalBeanFactory;
import org.mura.springframework.utils.StringValueResolver;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:02
 *
 * ConfigurableBeanFactory，可获取 BeanPostProcessor、BeanClassLoader 等的一个
 * 配置化接口。
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons() throws BeansException;

    /**
     * 为嵌入值（例如@Value注解）添加字符串解析器。
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 使用给定的字符串解析器解析给定的value中的表达式
     * 返回值是替换后的值
     */
    String resolveEmbeddedValue(String value);
}
