package org.mura.springframework.test;

import org.junit.Test;
import org.mura.springframework.aop.AdvisedSupport;
import org.mura.springframework.aop.TargetSource;
import org.mura.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.mura.springframework.aop.framework.Cglib2AopProxy;
import org.mura.springframework.aop.framework.JdkDynamicAopProxy;
import org.mura.springframework.test.bean.IUserService;
import org.mura.springframework.test.bean.UserService;
import org.mura.springframework.test.bean.UserServiceInterceptor;

import java.lang.reflect.Method;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 23:06
 */
public class ApiTest {
    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
//                *匹配任意字符，..放在参数中代表匹配任意参数的方法
                "execution(* org.mura.springframework.test.bean.UserService.*(..))");

        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void test_dynamic() {
//        目标对象
        IUserService userService = new UserService();

//        组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut(
                "execution(* org.mura.springframework.test.bean.IUserService.*(..))"));

//        代理对象（JdkDynamicAopProxy）
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();

//        测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

//        代理对象（Cglib2AopProxy）
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();

//        测试调用
        System.out.println("测试结果：" + proxy_cglib.register("Akutagawa Murasame"));
    }
}