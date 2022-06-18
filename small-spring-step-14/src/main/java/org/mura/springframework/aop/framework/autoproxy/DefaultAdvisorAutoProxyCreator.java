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
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
//        如果是代理别人的的类则返回（因为自己不需要被代理）
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();

//            不是需要代理的目标类则进行下一个判断
            if (!classFilter.matches(beanClass)) {
                continue;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

            // false为jdk动态代理
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
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
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        return propertyValues;
    }
}
