package com.khalid.ecommerce_system.form.validation;

import com.khalid.ecommerce_system.form.annotation.DateFormat;
import com.khalid.ecommerce_system.util.ParameterCheckUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
@Slf4j
public class DateValidator implements ConstraintValidator<DateFormat, String> {
    @Autowired ParameterCheckUtil parameterCheckUtil;

    @Override
    public void initialize(DateFormat dateFormat) {

    }

    @Override
    public boolean isValid(String dateAsString, ConstraintValidatorContext constraintValidatorContext) {
        log.info("START isValid");
        if (! StringUtils.hasText(dateAsString)) {
            log.info("END isValid parameter is empty");
            return true;
        }

        boolean result = parameterCheckUtil.checkDate(dateAsString);
        log.info("END isValid. result:{}", result);
        return result;
    }
}
