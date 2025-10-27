package ar.com.pichidev.template.common.rest.response;

public record ErrorResponse (
        String code,
        String message
){ }