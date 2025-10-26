package ar.com.pichidev.homestock.auth.core.usecase;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.core.entity.ProviderUserInformation;
import ar.com.pichidev.homestock.common.exception.FieldErrorCodeValues;
import ar.com.pichidev.homestock.common.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoogleOAuthLoginStrategyTest {

    private GoogleOAuthLoginStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new GoogleOAuthLoginStrategy();
    }

    @Test
    void getProviderIdReturnsGoogle() {
        OAuthProvider result = strategy.getProviderId();

        assertEquals(OAuthProvider.GOOGLE, result);
    }

    @Test
    void loginMapsProviderInformationCorrectly() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-user-123");
        providerInfo.put("email", "user@example.com");
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ProviderUserInformation result = strategy.login(providerInfo);

        assertNotNull(result);
        assertEquals("google-user-123", result.userId());
        assertEquals("user@example.com", result.email());
        assertEquals("John", result.name());
        assertEquals("Doe", result.lastname());
    }

    @Test
    void loginThrowsValidationExceptionWhenUserIdIsNull() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", null);
        providerInfo.put("email", "user@example.com");
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> strategy.login(providerInfo)
        );

        assertEquals(1, exception.getFieldErrors().size());
        assertEquals("userId", exception.getFieldErrors().getFirst().field());
        assertEquals(FieldErrorCodeValues.REQUIRED_FIELD, exception.getFieldErrors().getFirst().code());
    }

    @Test
    void loginThrowsValidationExceptionWhenEmailIsNull() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-user-123");
        providerInfo.put("email", null);
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> strategy.login(providerInfo)
        );

        assertEquals(1, exception.getFieldErrors().size());
        assertEquals("email", exception.getFieldErrors().getFirst().field());
        assertEquals(FieldErrorCodeValues.REQUIRED_FIELD, exception.getFieldErrors().getFirst().code());
    }

    @Test
    void loginThrowsValidationExceptionWhenNameIsNull() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-user-123");
        providerInfo.put("email", "user@example.com");
        providerInfo.put("given_name", null);
        providerInfo.put("family_name", "Doe");

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> strategy.login(providerInfo)
        );

        assertEquals(1, exception.getFieldErrors().size());
        assertEquals("name", exception.getFieldErrors().getFirst().field());
        assertEquals(FieldErrorCodeValues.REQUIRED_FIELD, exception.getFieldErrors().getFirst().code());
    }

    @Test
    void loginThrowsValidationExceptionWhenLastnameIsNull() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-user-123");
        providerInfo.put("email", "user@example.com");
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", null);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> strategy.login(providerInfo)
        );

        assertEquals(1, exception.getFieldErrors().size());
        assertEquals("lastname", exception.getFieldErrors().getFirst().field());
        assertEquals(FieldErrorCodeValues.REQUIRED_FIELD, exception.getFieldErrors().getFirst().code());
    }

    @Test
    void loginThrowsValidationExceptionWhenMultipleFieldsAreNull() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", null);
        providerInfo.put("email", null);
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> strategy.login(providerInfo)
        );

        assertEquals(2, exception.getFieldErrors().size());
        assertTrue(exception.getFieldErrors().stream()
                .anyMatch(error -> error.field().equals("userId")));
        assertTrue(exception.getFieldErrors().stream()
                .anyMatch(error -> error.field().equals("email")));
    }

    @Test
    void loginThrowsValidationExceptionWhenFieldsAreBlank() {
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "");
        providerInfo.put("email", "  ");
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> strategy.login(providerInfo)
        );

        assertEquals(2, exception.getFieldErrors().size());
        assertTrue(exception.getFieldErrors().stream()
                .anyMatch(error -> error.field().equals("userId")));
        assertTrue(exception.getFieldErrors().stream()
                .anyMatch(error -> error.field().equals("email")));
    }
}
