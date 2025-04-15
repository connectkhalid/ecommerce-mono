package com.khalid.ecommerce_system.form.annotation;

import com.khalid.ecommerce_system.form.validation.MailAddressValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(value = MailAddress.List.class)
@Documented
@Constraint(validatedBy = MailAddressValidator.class)
public @interface MailAddress {

    @Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        MailAddress[] value();
    }

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
