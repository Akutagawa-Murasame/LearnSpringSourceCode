package org.mura.springframework.beans.factory.config;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/22 16:51
 * 类引用
 */
public class BeanReference {
    String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}