package org.mura.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/24 21:27
 *
 * 作用域注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}