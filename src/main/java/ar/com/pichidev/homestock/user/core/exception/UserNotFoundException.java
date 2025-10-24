package ar.com.pichidev.homestock.user.core.exception;

import ar.com.pichidev.homestock.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(UserErrorValues.USER_NOT_FOUND, "User not found.");
    }
}
