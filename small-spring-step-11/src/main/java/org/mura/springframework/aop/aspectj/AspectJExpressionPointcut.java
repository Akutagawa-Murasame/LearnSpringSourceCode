package org.mura.springframework.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.mura.springframework.aop.ClassFilter;
import org.mura.springframework.aop.MethodMatcher;
import org.mura.springframework.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/7 11:19
 *
 * 此处引入了aspectjweaver依赖
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private static final Set<PointcutPrimitive> SUPPORTED_PRiMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRiMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    /**
     * 保存execution的表达式对象
     */
    private final PointcutExpression pointcutExpression;

    /**
     * 解析execution开头的表达式
     */
    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORTED_PRiMITIVES, this.getClass().getClassLoader()
                );

        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 匹配类
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 匹配方法
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
