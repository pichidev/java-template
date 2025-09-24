package ar.com.pichidev.homestock.auth.core.exception;

import ar.com.pichidev.homestock.common.exception.BusinessException;

public class NotImplementedOAuthStrategy extends BusinessException {
    public NotImplementedOAuthStrategy() {
        super(AuthErrorValues.INVALID_PROVIDER, "Invalid OAuth provider");
    }
}
