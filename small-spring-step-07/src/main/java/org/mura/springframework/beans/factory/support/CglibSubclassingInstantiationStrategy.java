package org.mura.springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author Akutagawa Murasame
 * Cglib实例化，他是通过实现子类或者实现接口实现的
 *
 * 其实 Cglib 创建有构造函数的 Bean 也非常方便，在这里我们更加简化的处理
 * 了，如果你阅读 Spring 源码还会看到 CallbackFilter 等实现，不过我们目前的方
 * 式并不会影响创建。不过beanName暂时没看到有什么用
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName,
                              Constructor constructor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(beanDefinition.getBeanClass());

//        NoOp表示不设置回调
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        if (null == constructor) {
//            其实没增强，调试可以发现返回值是enhancer包裹的原对象
            return enhancer.create();
        }

        return enhancer.create(constructor.getParameterTypes(), args);
    }
}
