package org.mura.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author Akutagawa Murasame
 * @date 2022/6/16 19:23
 *
 * 从配置文件读取字符串配置注入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Documented
public @interface Value {
//    value写的是Spring的EL表达式
    String value();
}
