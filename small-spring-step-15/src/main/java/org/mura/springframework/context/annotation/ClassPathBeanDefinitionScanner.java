package org.mura.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.mura.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.mura.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/24 21:26
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
//                解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);

                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }

                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

//        将处理注解的BeanPostProcessor添加到容器
        registry.registerBeanDefinition("org.mura.springframework.context.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();

        Scope scope = beanClass.getAnnotation(Scope.class);

        if (null != scope) {
            return scope.value();
        }

        return StrUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();

        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }

        return value;
    }
}