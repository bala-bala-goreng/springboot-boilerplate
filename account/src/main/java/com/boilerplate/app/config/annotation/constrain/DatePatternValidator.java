package com.boilerplate.app.config.annotation.constrain;

import com.boilerplate.app.config.annotation.DatePattern;
import com.boilerplate.app.constant.DateFormatEnum;
import com.boilerplate.app.util.ValidationUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatePatternValidator implements ConstraintValidator<DatePattern, String> {

    private final ValidationUtil validationUtil;

    private DateFormatEnum formatEnum;

    @Override
    public void initialize(DatePattern constraintAnnotation) {
        this.formatEnum = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Allow null or blank values, as they can be handled by @NotBlank or @NotNull
        }

        return validationUtil.isDateFormatValid(value, formatEnum);
    }

}

