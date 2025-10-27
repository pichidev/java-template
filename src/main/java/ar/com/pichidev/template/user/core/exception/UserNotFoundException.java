package ar.com.pichidev.template.user.core.exception;

import ar.com.pichidev.template.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(UserErrorValues.USER_NOT_FOUND, "User not found.");
    }
}
