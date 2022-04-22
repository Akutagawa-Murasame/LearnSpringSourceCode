package org.mura.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.mura.springframework.beans.factory.DisposableBean;
import org.mura.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/4 15:08
 *
 * 销毁方法适配器(接口和配置)，可以类比为BeanDefinition
 *
 * 可能你会想这里怎么有一个适配器的类呢，因为销毁方法有两种甚至多种方式，目
 * 前有实现接口 DisposableBean、配置信息 destroy-method，两种方
 * 式。而这两种方式的销毁动作是由 AbstractApplicationContext 在注册虚拟机钩子
 * 后，虚拟机关闭前执行的操作动作。
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
//        1、如果这个bean是销毁接口，就把这个bean实现的destroy方法调用
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

//        2、对于所有的bean（包括初始化和销毁接口），调用destroy之外的销毁函数，防止重复销毁
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean &&
                "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            destroyMethod.invoke(bean);
        }
    }
}
