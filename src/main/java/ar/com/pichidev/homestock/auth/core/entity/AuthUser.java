package ar.com.pichidev.homestock.auth.core.entity;

import java.util.UUID;

public record AuthUser(
        UUID id,
        String email,
        String name,
        String lastname
) {

    public AuthUser(String email, String name, String lastname) {
        this(UUID.randomUUID(), email, name, lastname);
    }
}
