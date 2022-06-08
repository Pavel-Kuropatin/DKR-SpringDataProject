package by.kuropatin.dkr.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public final class BigDecimalPriceValidator implements ConstraintValidator<BigDecimalInRange, BigDecimal> {

    private boolean nullable;
    private BigDecimal min;
    private BigDecimal max;

    @Override
    public void initialize(BigDecimalInRange annotation) {
        nullable = annotation.nullable();
        min = BigDecimal.valueOf(annotation.min());
        max = BigDecimal.valueOf(annotation.max());
    }

    @Override
    public boolean isValid(final BigDecimal value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return nullable;
        }
        try {
            return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}