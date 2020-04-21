package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return getViolations(entity).isEmpty();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> getViolations(T entity) {
        return validator.validate(entity);
    }
}
