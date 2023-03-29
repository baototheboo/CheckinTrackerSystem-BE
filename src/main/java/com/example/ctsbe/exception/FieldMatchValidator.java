package com.example.ctsbe.exception;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String field;
    private String fieldMatch;
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        field = constraintAnnotation.field();
        fieldMatch = constraintAnnotation.fieldMatch();
    }
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        final Object fieldValue = beanWrapper.getPropertyValue(field);
        final Object fieldMatchValue = beanWrapper.getPropertyValue(fieldMatch);
        return fieldValue != null && fieldValue.equals(fieldMatchValue);
    }
}

