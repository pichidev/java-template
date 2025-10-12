package ar.com.pichidev.homestock.common.rest.response;

public record ErrorResponse (
        String code,
        String message
){ }