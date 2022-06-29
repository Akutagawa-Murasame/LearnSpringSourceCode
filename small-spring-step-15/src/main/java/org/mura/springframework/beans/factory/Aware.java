package org.mura.springframework.beans.factory;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/20 18:38
 *
 * 在 Spring 中有特别多类似这样的标记接口的设计方式，它们的存在就像是一种标
 * 签一样，可以方便统一摘取出属于此类接口的实现类，通常会有 instanceof 一起
 * 判断使用。
 */
public interface Aware {
}
