package org.zlatenov.accountmanaging.exception;

import org.zlatenov.accountmanaging.model.dto.UserDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author Angel Zlatenov
 */

public class InvalidUserException extends Throwable {
    public InvalidUserException(Set<ConstraintViolation<UserDto>> violations) {
    }
}
