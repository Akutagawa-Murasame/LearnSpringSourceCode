package org.mura.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/24 21:27
 */
public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }

        return candidates;
    }
}