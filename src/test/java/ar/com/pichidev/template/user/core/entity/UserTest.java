package ar.com.pichidev.template.user.core.entity;

import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.common.exception.FieldErrorCodeValues;
import ar.com.pichidev.template.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createAndValidate_shouldCreateValidUser() {
        UUID id = UUID.randomUUID();
        User user = User.createAndValidate(id, "Agustin", "Carrizo", "agustin@example.com", Set.of(Roles.USER));

        assertNotNull(user);
        assertEquals("Agustin", user.getName());
        assertEquals("Carrizo", user.getLastName());
        assertEquals("agustin@example.com", user.getEmail());
        assertTrue(user.getRoles().contains(Roles.USER));
        assertEquals(id, user.getId());
    }

    @Test
    void createAndValidate_shouldGenerateIdIfNull() {
        User user = User.createAndValidate(null, "Agustin", "Carrizo", "agustin@example.com");

        assertNotNull(user.getId());
        assertEquals("Agustin", user.getName());
        assertEquals("Carrizo", user.getLastName());
        assertEquals("agustin@example.com", user.getEmail());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    void createAndValidate_shouldThrowValidationExceptionWhenNameIsNull() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                User.createAndValidate(UUID.randomUUID(), null, "Carrizo", "agustin@example.com")
        );
        assertTrue(ex.getFieldErrors().stream().anyMatch(f -> f.field().equals("name")));
    }

    @Test
    void createAndValidate_shouldThrowValidationExceptionWhenLastNameIsNull() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                User.createAndValidate(UUID.randomUUID(), "Agustin", null, "agustin@example.com")
        );
        assertTrue(ex.getFieldErrors().stream().anyMatch(f -> f.field().equals("lastName")));
    }

    @Test
    void createAndValidate_shouldThrowValidationExceptionWhenEmailIsNull() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                User.createAndValidate(UUID.randomUUID(), "Agustin", "Carrizo", null)
        );
        assertTrue(ex.getFieldErrors().stream().anyMatch(f -> f.field().equals("email")));
    }

    @Test
    void createAndValidate_shouldThrowValidationExceptionWhenEmailIsInvalid() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
                User.createAndValidate(UUID.randomUUID(), "Agustin", "Carrizo", "invalid-email")
        );
        assertTrue(ex.getFieldErrors().stream().anyMatch(f -> f.field().equals("email")));
        assertTrue(ex.getFieldErrors().stream().anyMatch(f -> f.code() == FieldErrorCodeValues.INVALID_FORMAT));
    }

    @Test
    void assignRoles_shouldAddRoles() {
        User user = User.createAndValidate(UUID.randomUUID(), "Agustin", "Carrizo", "agustin@example.com");

        assertTrue(user.getRoles().isEmpty());

        user.assignRoles(Set.of(Roles.USER, Roles.ADMIN));

        assertTrue(user.getRoles().contains(Roles.USER));
        assertTrue(user.getRoles().contains(Roles.ADMIN));
    }

    @Test
    void fromPersistence_shouldCreateUserFromData() {
        UUID id = UUID.randomUUID();
        User user = User.fromPersistence(id, "Agustin", "Carrizo", "agustin@example.com", Set.of(Roles.USER));

        assertEquals(id, user.getId());
        assertEquals("Agustin", user.getName());
        assertEquals("Carrizo", user.getLastName());
        assertEquals("agustin@example.com", user.getEmail());
        assertTrue(user.getRoles().contains(Roles.USER));
    }
}
