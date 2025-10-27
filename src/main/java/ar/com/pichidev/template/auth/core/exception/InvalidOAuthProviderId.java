package ar.com.pichidev.template.auth.core.exception;

import ar.com.pichidev.template.common.exception.BusinessException;

public class InvalidOAuthProviderId extends BusinessException {
    public InvalidOAuthProviderId() {
        super(AuthErrorValues.NOT_IMPLEMENTED_OAUTH_STRATEGY, "Not implemented OAuth Strategy.");
    }
}
