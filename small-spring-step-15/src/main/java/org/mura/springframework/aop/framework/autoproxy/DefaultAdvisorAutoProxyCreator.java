package org.mura.springframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.mura.springframework.aop.*;
import org.mura.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.mura.springframework.aop.framework.ProxyFactory;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValues;
import org.mura.springframework.beans.factory.BeanFactory;
import org.mura.springframework.beans.factory.BeanFactoryAware;
import org.mura.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:14
 *
 * 融入 Bean 生命周期的自动代理创建者
 *
 *  这个 DefaultAdvisorAutoProxyCreator 类的主要核心实现在于
 * postProcessBeforeInstantiation 方法中，从通过 beanFactory.getBeansOfType 获
 * 取 AspectJExpressionPointcutAdvisor 开始。
 *  获取了 advisors 以后就可以遍历相应的 AspectJExpressionPointcutAdvisor 填充对
 * 应的属性信息，包括：目标对象、拦截方法、匹配器，之后返回代理对象即可。
 *  那么现在调用方获取到的这个 Bean 对象就是一个已经被切面注入的对象了，当调
 * 用方法的时候，则会被按需拦截，处理用户需要的信息。
 *
 * small-spring-step-15在这个类进行了如下改动：
 * 1、编写了Object postProcessAfterInitialization(Object bean, String beanName)的方法体
 * 2、删除了Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)的方法体，因为要对如果代理类进行属性注入，无法在bean实例化之前进行代理
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        不是bean就不需要代理
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤非匹配类
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }

//        一个代理类都没有，那就只能返回原bean喽
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        return propertyValues;
    }
}
