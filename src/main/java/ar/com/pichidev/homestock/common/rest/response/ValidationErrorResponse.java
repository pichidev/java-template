package ar.com.pichidev.homestock.common.rest.response;

import ar.com.pichidev.homestock.common.exception.FieldError;

import java.util.List;

public record ValidationErrorResponse(
        String code,
        String message,
        List<FieldError> fieldErrors
){ }