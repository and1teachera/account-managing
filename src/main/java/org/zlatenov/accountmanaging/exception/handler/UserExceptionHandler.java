package org.zlatenov.accountmanaging.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zlatenov.accountmanaging.exception.ErrorResponse;
import org.zlatenov.accountmanaging.exception.InvalidUserException;

import java.util.ArrayList;
import java.util.List;

import static org.zlatenov.accountmanaging.constants.Messages.INCORRECT_REQUEST;

/**
 * @author Angel Zlatenov
 */
@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidUserException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(InvalidUserException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
