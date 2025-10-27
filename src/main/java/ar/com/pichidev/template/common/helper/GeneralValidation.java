package ar.com.pichidev.template.common.helper;

import ar.com.pichidev.template.common.exception.FieldError;
import ar.com.pichidev.template.common.exception.FieldErrorCodeValues;

import java.util.Optional;

public class GeneralValidation {
    public static Optional<FieldError> validateRequiredField(Object value, String field) {
        if (value == null) {
            return Optional.of(new FieldError(
                    field,
                    "The field '" + field + "' is required and cannot be null.",
                    FieldErrorCodeValues.REQUIRED_FIELD
            ));
        }

        if (value instanceof String str && str.isBlank()) {
            return Optional.of(new FieldError(
                    field,
                    "The field '" + field + "' cannot be blank.",
                    FieldErrorCodeValues.REQUIRED_FIELD
            ));
        }

        return Optional.empty();
    }
}
