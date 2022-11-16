package ca.lichangzhang.SuperheroSighting.service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class DoubleNotNullValidator implements
        ConstraintValidator<DoubleNotNullConstraint, String> {

    private Boolean notEmpty;
    private Integer min;
    private Integer max;
    private String messageNotEmpty;
    private String messageNumeric;
    private String messageLength;
    private String messageNotZero;

    @Override
    public void initialize(DoubleNotNullConstraint field) {
        notEmpty = field.notEmpty();
        min = field.min();
        max = field.max();
        messageNumeric = field.messageNumeric();
        messageNotEmpty = field.message();
        messageLength = field.messageLength();
        messageNotZero = field.messageNotZero();
    }

    @Override
    public boolean isValid(String value,
            ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (notEmpty && value.isEmpty()) {
            context.buildConstraintViolationWithTemplate(messageNotEmpty).addConstraintViolation();
            return false;
        }
        if ((min > 0 || max < Integer.MAX_VALUE) && (value.length() < min || value.length() > max)) {
            context.buildConstraintViolationWithTemplate(messageLength).addConstraintViolation();
            return false;
        }
        if (!(value.matches("^[-+]?\\d+(\\.\\d+)?$"))) {
            context.buildConstraintViolationWithTemplate(messageNumeric).addConstraintViolation();
            return false;
        }
        if (!(value.matches("^(-?.*[1-9])\\d*\\.?\\d*$"))) {
            context.buildConstraintViolationWithTemplate(messageNotZero).addConstraintViolation();
            return false;
        }
        return true;
    }
}
