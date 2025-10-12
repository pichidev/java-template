package ar.com.pichidev.homestock.user.core.exception;

import ar.com.pichidev.homestock.common.exception.BusinessException;

public class EmailAlreadyExist extends BusinessException {
    public EmailAlreadyExist(String message) {
        super(message);
    }
}
