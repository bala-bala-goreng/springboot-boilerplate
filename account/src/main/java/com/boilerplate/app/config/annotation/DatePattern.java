package com.boilerplate.app.config.annotation;

import com.boilerplate.app.config.annotation.constrain.DatePatternValidator;
import com.boilerplate.app.constant.DateFormatEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DatePatternValidator.class)
public @interface DatePattern {
    DateFormatEnum format();
    String message() default "Invalid date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

