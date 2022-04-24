package org.mura.springframework.beans.factory;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/4 15:10
 */
public interface DisposableBean {
    /**
     * Bean销毁时调用
     *
     * DisposableBean接口方法还是比较常用的，在一些需要结
     * 合 Spring 实现的组件中，经常会使用这个方法来做一些参数的销毁
     * 操作。比如接口暴漏、数据库数据读取、配置文件加载等等。
     */
    void destroy() throws Exception;
}
