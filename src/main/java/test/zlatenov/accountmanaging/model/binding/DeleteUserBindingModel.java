package test.zlatenov.accountmanaging.model.binding;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static test.zlatenov.accountmanaging.constants.Messages.VALUE_IS_NOT_VALID;
import static test.zlatenov.accountmanaging.constants.Messages.VALUE_IS_REQUIRED;

/**
 * @author Angel Zlatenov
 */
@Data
public class DeleteUserBindingModel {

    @NotNull(message = VALUE_IS_REQUIRED)
    @Email(message = VALUE_IS_NOT_VALID)
    private final String email;
}
