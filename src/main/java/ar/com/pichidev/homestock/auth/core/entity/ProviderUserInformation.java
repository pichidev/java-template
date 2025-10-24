package ar.com.pichidev.homestock.auth.core.entity;

public record ProviderUserInformation(
        String userId,
        String email,
        String name,
        String lastname
) {
}
