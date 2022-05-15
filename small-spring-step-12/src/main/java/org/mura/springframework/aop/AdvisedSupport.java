package org.mura.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:21
 *
 * AdvisedSupport，主要是用于把代理、拦截、匹配的各项属性包装到一个类中，方
 * 便在 Proxy 实现类进行使用。
 */
public class AdvisedSupport {
    /**
     * proxyConfig
     */
    private boolean proxyTargetClass = false;

    /**
     * 被代理的目标对象
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器，是一个具体拦截方法实现类，由用户子集实现
     * MethodInterceptor#invoke做具体的处理
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器（检查目标方法是否符合通知条件）
     * 这个对象由 AspectJExpressionPointcut
     * 提供服务。
     */
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
