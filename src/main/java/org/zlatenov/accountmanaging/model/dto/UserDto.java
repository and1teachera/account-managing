package org.zlatenov.accountmanaging.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static org.zlatenov.accountmanaging.constants.Messages.VALUE_IS_NOT_VALID;
import static org.zlatenov.accountmanaging.constants.Messages.VALUE_IS_REQUIRED;

/**
 * @author Angel Zlatenov
 */
@Data
public class UserDto {

    @NotNull(message = VALUE_IS_REQUIRED)
    @Length(min = 3, max = 20, message = VALUE_IS_NOT_VALID)
    private final String firstName;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Length(min = 3, max = 20, message = VALUE_IS_NOT_VALID)
    private final String lastName;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Email(message = VALUE_IS_NOT_VALID)
    private final String email;

    @NotNull(message = VALUE_IS_REQUIRED)
    private final String birthDate;
}
