package by.kuropatin.dkr.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class AgeValidator implements ConstraintValidator<Age, CharSequence> {

    private int minAge;

    @Override
    public void initialize(Age annotation) {
        minAge = annotation.minAge();
    }

    @Override
    public boolean isValid(final CharSequence date, final ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        try {
            final LocalDate birthDate = LocalDate.parse(date);
            final LocalDate now = LocalDate.now();
            return !birthDate.plusYears(minAge).isAfter(now);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}