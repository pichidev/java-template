package ar.com.pichidev.template.auth.core.exception;

import ar.com.pichidev.template.common.exception.BusinessException;

public class NotImplementedOAuthStrategy extends BusinessException {
    public NotImplementedOAuthStrategy() {
        super(AuthErrorValues.INVALID_PROVIDER, "Invalid OAuth provider");
    }
}
