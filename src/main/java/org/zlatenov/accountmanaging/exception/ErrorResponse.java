package org.zlatenov.accountmanaging.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Angel Zlatenov
 */
@Data
@AllArgsConstructor
public class ErrorResponse{
    private String message;
    private List<String> details;

}
