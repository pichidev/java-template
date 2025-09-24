package ar.com.pichidev.homestock.auth.core.exception;

import ar.com.pichidev.homestock.common.exception.BusinessException;

public class InvalidOAuthProviderId extends BusinessException {
    public InvalidOAuthProviderId() {
        super(AuthErrorValues.NOT_IMPLEMENTED_OAUTH_STRATEGY, "Not implemented OAuth Strategy.");
    }
}
