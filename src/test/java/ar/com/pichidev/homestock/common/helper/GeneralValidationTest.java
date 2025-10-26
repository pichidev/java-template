package ar.com.pichidev.homestock.common.helper;

import ar.com.pichidev.homestock.common.exception.FieldError;
import ar.com.pichidev.homestock.common.exception.FieldErrorCodeValues;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GeneralValidationTest {

    @Test
    void validateRequiredFieldReturnsEmptyWhenValueIsNotNullOrBlank() {
        Optional<FieldError> result = GeneralValidation.validateRequiredField("Valid Value", "fieldName");
        assertTrue(result.isEmpty());
    }

    @Test
    void validateRequiredFieldReturnsErrorWhenValueIsNull() {
        Optional<FieldError> result = GeneralValidation.validateRequiredField(null, "fieldName");
        assertTrue(result.isPresent());
        FieldError error = result.get();
        assertEquals("fieldName", error.field());
        assertEquals("The field 'fieldName' is required and cannot be null.", error.message());
        assertEquals(FieldErrorCodeValues.REQUIRED_FIELD, error.code());
    }

    @Test
    void validateRequiredFieldReturnsErrorWhenValueIsBlankString() {
        Optional<FieldError> result = GeneralValidation.validateRequiredField("   ", "fieldName");
        assertTrue(result.isPresent());
        FieldError error = result.get();
        assertEquals("fieldName", error.field());
        assertEquals("The field 'fieldName' cannot be blank.", error.message());
        assertEquals(FieldErrorCodeValues.REQUIRED_FIELD, error.code());
    }

    @Test
    void validateRequiredFieldReturnsEmptyWhenValueIsNonBlankString() {
        Optional<FieldError> result = GeneralValidation.validateRequiredField("Non-blank", "fieldName");
        assertTrue(result.isEmpty());
    }
}
