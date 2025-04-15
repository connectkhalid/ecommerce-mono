package com.khalid.ecommerce_system.form.annotation;

import com.khalid.ecommerce_system.form.validation.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(value = DateFormat.List.class)
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface DateFormat {

    @Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        DateFormat[] value();
    }

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
