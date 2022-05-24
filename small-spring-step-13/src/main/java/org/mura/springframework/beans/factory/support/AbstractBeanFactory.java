package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.FactoryBean;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.BeanPostProcessor;
import org.mura.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akutagawa Murasame
 * AbstractBeanFactory 首先继承了 DefaultSingletonBeanRegistry，也就具备了使用
 * 单例注册类方法。
 * - 接下来很重要的一点是关于接口 BeanFactory 的实现，在方法 getBean 的实现过
 * 程中可以看到，主要是对单例 Bean 对象的获取以及在获取不到时需要拿到 Bean
 * 的定义做相应 Bean 实例化操作。那么 getBean 并没有自身的去实现这些方法，
 * 而是只定义了调用过程以及提供了抽象方法，由实现此抽象类的其他类做相应实
 * 现。
 * - 后续继承抽象类 AbstractBeanFactory 的类有两个，包括：
 * AbstractAutowireCapableBeanFactory、DefaultListableBeanFactory，这两个类分别
 * 做了相应的实现处理
 *
 * small-spring-step-09这个类不直接继承DefaultSingletonBeanRegistry，
 * 中间加了一层FactoryBeanRegistrySupport，主要是处理关于 FactoryBean
 * 注册的支撑操作。因为需要扩展出创建 FactoryBean 对象的能力，所以这就像一个
 * 链条服务上，截出一个段来处理额外的服务，并把链条再链接上。
 */
@SuppressWarnings("uncheck")
public abstract class AbstractBeanFactory
        extends FactoryBeanRegistrySupport
        implements ConfigurableBeanFactory {
//    要用到的BeanPostProcessor（不止一个）
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * FactoryBean的缓存和普通Bean的缓存不在同一个Map中
     */
    protected <T> T doGetBean(final String name, final Object[] args) throws BeansException {
//        尝试获取单例，无论这个单例是否是FactoryBean类型的
        Object sharedInstance = getSingleton(name);

//        有该单例对象，在下一步中判断该单例是否是FactoryBean并返回
        if (sharedInstance != null) {
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

//        否则，按照方法创建bean
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) throws BeansException {
//        如果这个bean不是FactoryBean，就直接返回
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

//        否则需要调用FactoryBean的getObject方法来获取增强bean
        Object object = getCachedObjectForFactoryBean(beanName);

//        缓存中没有，那么就要创建
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        if (!this.beanPostProcessors.contains(beanPostProcessor)) {
            this.beanPostProcessors.add(beanPostProcessor);
        }
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}