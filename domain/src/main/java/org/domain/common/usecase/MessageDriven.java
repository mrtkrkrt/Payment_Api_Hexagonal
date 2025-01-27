package org.domain.common.usecase;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface MessageDriven {
    Class<? extends Message> message();

    String method() default "handle";
}
