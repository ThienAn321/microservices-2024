package com.microservices.loan.validator.enums;

import com.microservices.loan.constants.CommonConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidateEnumValue, CharSequence> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidateEnumValue annotation) {
        enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(CommonConstants.INVALID_VALIDATE).addConstraintViolation();
        try {
            Enum<?> enumValue = findEnumConstant(enumClass, value.toString());
            return enumValue != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static Enum<?> findEnumConstant(Class<? extends Enum<?>> enumClass, String value) {
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equals(value)) {
                return enumConstant;
            }
        }
        return null;
    }
}
