package org.mura.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValue;
import org.mura.springframework.beans.PropertyValues;
import org.mura.springframework.beans.factory.*;
import org.mura.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.BeanPostProcessor;
import org.mura.springframework.beans.factory.config.BeanReference;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Akutagawa Murasame
 * 因为AbstractAutowireCapableBeanFactory 本身也是一个抽象类，所以它只会实现属于
 * 自己的抽象方法，其他抽象方法由继承 AbstractAutowireCapableBeanFactory 的
 * 类实现。这里就体现了类实现过程中的各司其职，你只需要关心属于你的内容，不
 * 是你的内容，不要参与。
 * <p>
 * 在 AbstractAutowireCapableBeanFactory 类中实现了 Bean 的实例化操作
 * newInstance，其实这块会埋下一个坑，有构造函数入参的对象怎么处理？可以
 * 提前思考
 * - 在处理完 Bean 对象的实例化后，直接调用 addSingleton 方法存放到单例对
 * 象的缓存中去。
 * <p>
 * 现在通过定义bean的实例化策略来避免构造函数有参数的问题
 * 我们选择了Cglib增强代理，这个在spring中其实是可选的，以后再说
 * <p>
 * step-04改动的类主要是 AbstractAutowireCapableBeanFactory，在
 * createBean 中补全属性填充部分。
 * <p>
 * step-06在这个类中加入了bean的前置和后置处理
 * <p>
 * step-07在这个类中加入了初始化钩子，完成了06中的待完成内容
 * <p>
 * step-08在这个类中加入了对于aware的感知操作
 * <p>
 * step-09在这个类进行了是否单例的判断，如果是单例的，则不在内存中删除
 */
public abstract class AbstractAutowireCapableBeanFactory
        extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;

        try {
            bean = createBeanInstance(beanDefinition, beanName, args);

//                    给bean填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

//             执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
//            所以返回的bean是包装后的bean
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

//        注册实现了 DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

//        判断是否单例，如果是单例则添加到map，防止gc清除
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }

        return bean;
    }

    /**
     * 在创建 Bean 对象的实例的时候，需要把销毁方法保存起来，方便后续执行销毁动
     * 作进行调用。
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean,
                                                     BeanDefinition beanDefinition) {
//        非Singleton类型的Bean不执行销毁方法
        if (!beanDefinition.isSingleton()) {
            return;
        }

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 初始化bean，并不是实例化bean，进行这一步时bean已经实例化好了
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        // 执行BeanFactory、BeanClassLoaderAware、BeanClassLoaderAware、BeanNameAware的感知
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }

            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(bean.getClass().getClassLoader());
            }

            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

//         执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

//         执行bean的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method bean[" + beanName + "] failed", e);
        }

//         执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
//        1、如果这个bean是初始化接口，就把这个bean实现的afterPropertiesSet方法调用
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

//        2、对所有注册了的bean（包括初始化和销毁接口）调用init方法，init方法没有参数
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            initMethod.invoke(wrappedBean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

//        一层一层的包装
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);

//            有点儿意思
            if (null == current) {
                return result;
            }

            result = current;
        }

        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

//        一层一层的包装
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);

            if (null == current) {
                return result;
            }

            result = current;
        }

        return result;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object... args) throws BeansException {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();

//        获取bean的所有构造方法
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

//        这里只比较了参数的数量，而实际 Spring 源码中还需要比对入参
//        类型，否则相同数量不同入参类型的情况，就会抛异常了。
        for (Constructor<?> constructor : declaredConstructors) {
            if (null != args && constructor.getParameterTypes().length == args.length) {
                constructorToUse = constructor;
                break;
            }
        }

        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * bean属性填充
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

//                如果是引用类型，循环依赖问题，当前版本其实没用到这个，这个内容较多，后续版本再写
                if (value instanceof BeanReference) {
//                    A 依赖 B，获取 B 的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

//                属性填充
//                引入了hutool依赖
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (BeansException e) {
            throw new BeansException("Error setting properties values:" + beanName);
        }
    }
}
