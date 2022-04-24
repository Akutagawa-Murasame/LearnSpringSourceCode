package org.mura.springframework.context;

import org.mura.springframework.beans.factory.ListableBeanFactory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:51
 *
 * ApplicationContext，继承于 ListableBeanFactory，也就继承了关于 BeanFactory
 * 方法，比如一些 getBean 的方法。另外 ApplicationContext 本身是 Central 接
 * 口，但目前还不需要添加一些获取的 ID 和父类上下文，所以暂时没有接口方法的定
 * 义。
 */
public interface ApplicationContext extends ListableBeanFactory {
}