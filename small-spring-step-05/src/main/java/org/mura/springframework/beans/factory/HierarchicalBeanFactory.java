package org.mura.springframework.beans.factory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:09
 *
 * 在 Spring 源码中它有两个方法
 * 1、第一个方法返回本Bean工厂的父工厂。这个方法实现了工厂的分层。
 * 2、第二个方法判断本地工厂是否包含这个Bean（忽略其他所有父工厂）。这也是分层思想的体现。
 */
public interface HierarchicalBeanFactory extends BeanFactory {
}
