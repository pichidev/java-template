package ar.com.pichidev.homestock.user.core.exception;

import ar.com.pichidev.homestock.common.exception.BusinessException;

public class EmailAlreadyExistException extends BusinessException {
    public EmailAlreadyExistException() {
        super(UserErrorValues.EMAIL_ALREADY_EXIST, "Email already exist");
    }
}
