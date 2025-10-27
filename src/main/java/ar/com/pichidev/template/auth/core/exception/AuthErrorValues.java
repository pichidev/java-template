package ar.com.pichidev.template.auth.core.exception;

import ar.com.pichidev.template.common.exception.CodeError;

public enum AuthErrorValues implements CodeError {
    NOT_IMPLEMENTED_OAUTH_STRATEGY,
    INVALID_PROVIDER,
}
