package org.zlatenov.accountmanaging.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static org.zlatenov.accountmanaging.constants.Messages.INVALID_DATA;
import static org.zlatenov.accountmanaging.constants.Messages.VALUE_IS_REQUIRED;

/**
 * @author Angel Zlatenov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = VALUE_IS_REQUIRED)
    @Length(min = 3, max = 20, message = INVALID_DATA)
    private String firstName;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Length(min = 3, max = 20, message = INVALID_DATA)
    private String lastName;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Email(message = INVALID_DATA)
    private String email;

    @NotNull(message = VALUE_IS_REQUIRED)
    private String birthDate;
}
