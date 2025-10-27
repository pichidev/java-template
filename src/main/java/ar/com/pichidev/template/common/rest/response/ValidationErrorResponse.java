package ar.com.pichidev.template.common.rest.response;

import ar.com.pichidev.template.common.exception.FieldError;

import java.util.List;

public record ValidationErrorResponse(
        String code,
        String message,
        List<FieldError> fieldErrors
){ }