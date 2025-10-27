package ar.com.pichidev.template.common.exception;

public enum FieldErrorCodeValues implements CodeError {
    INVALID_FIELD,
    REQUIRED_FIELD,
    INVALID_FORMAT,
    VALUE_TOO_LONG,
    VALUE_TOO_SHORT,
    OUT_OF_RANGE,
    DUPLICATE_VALUE
}
