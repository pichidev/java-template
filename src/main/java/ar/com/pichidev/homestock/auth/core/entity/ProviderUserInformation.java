package ar.com.pichidev.homestock.auth.core.entity;

import ar.com.pichidev.homestock.common.exception.FieldError;
import ar.com.pichidev.homestock.common.exception.ValidationException;
import ar.com.pichidev.homestock.common.helper.GeneralValidation;

import java.util.ArrayList;
import java.util.List;

public record ProviderUserInformation(
        String userId,
        String email,
        String name,
        String lastname
) {
    public ProviderUserInformation {
        List<FieldError> errors = new ArrayList<>();

        GeneralValidation.validateRequiredField(userId, "userId").ifPresent(errors::add);
        GeneralValidation.validateRequiredField(email, "email").ifPresent(errors::add);
        GeneralValidation.validateRequiredField(name, "name").ifPresent(errors::add);
        GeneralValidation.validateRequiredField(lastname, "lastname").ifPresent(errors::add);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}