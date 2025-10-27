package ar.com.pichidev.template.user.core.exception;

import ar.com.pichidev.template.common.exception.BusinessException;

public class EmailAlreadyExistException extends BusinessException {
    public EmailAlreadyExistException() {
        super(UserErrorValues.EMAIL_ALREADY_EXIST, "Email already exist");
    }
}
