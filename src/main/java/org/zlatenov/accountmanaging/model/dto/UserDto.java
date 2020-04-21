package org.zlatenov.accountmanaging.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.zlatenov.accountmanaging.constants.Messages.INVALID_BIRTH_DATE;
import static org.zlatenov.accountmanaging.constants.Messages.INVALID_EMAIL;
import static org.zlatenov.accountmanaging.constants.Messages.INVALID_FIRST_NAME;
import static org.zlatenov.accountmanaging.constants.Messages.INVALID_LAST_NAME;
import static org.zlatenov.accountmanaging.constants.Messages.VALUE_IS_REQUIRED;

/**
 * @author Angel Zlatenov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = VALUE_IS_REQUIRED)
    @Length(min = 3, max = 20, message = INVALID_FIRST_NAME)
    private String firstName;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Length(min = 3, max = 20, message = INVALID_LAST_NAME)
    private String lastName;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Email(message = INVALID_EMAIL)
    private String email;

    @NotNull(message = VALUE_IS_REQUIRED)
    @Pattern(regexp="\\d{4}[-]\\d{2}[-]\\d{2}", message = INVALID_BIRTH_DATE)
    private String birthDate;
}
