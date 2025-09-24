package ar.com.pichidev.homestock.auth.core.exception;

import ar.com.pichidev.homestock.common.exception.CodeError;

public enum AuthErrorValues implements CodeError {
    NOT_IMPLEMENTED_OAUTH_STRATEGY,
    INVALID_PROVIDER,
}
