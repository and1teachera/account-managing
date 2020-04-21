package org.zlatenov.accountmanaging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Angel Zlatenov
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}
