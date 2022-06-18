package org.mura.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/24 21:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}