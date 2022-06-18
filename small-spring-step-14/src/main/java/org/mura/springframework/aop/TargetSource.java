package org.mura.springframework.aop;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:22
 *
 * 装载了被代理类的信息
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取被代理类实现的接口
     */
    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    /**
     * 获取被代理类
     */
    public Object getTarget(){
        return this.target;
    }
}