package org.mura.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author Akutagawa Murasame
 * @date 2022/6/16 19:23
 *
 * 自动注入,默认按照bean名称注入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
