package by.kuropatin.dkr.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.FIELD;

/**
 * The annotated element must not be {@code null} and must be an {@link BigDecimal} in range (including) between {@code min} and {@code max}.
 * <p>
 * Accepts {@link CharSequence}.
 */
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BigDecimalPriceValidator.class)
public @interface BigDecimalInRange {

    /**
     * @return if annotated element can be null
     */
    boolean nullable() default true;

    /**
     * @return the minimum value of annotated element
     */
    double min();

    /**
     * @return the maximum value of annotated element
     */
    double max();

    /**
     * @return the error message template
     */
    String message() default "Number must be integer in specified range";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};
}