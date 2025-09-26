package ar.com.pichidev.homestock.auth.core.entity;

import java.util.UUID;

public record AuthIdentity(
        UUID id,
        OAuthProvider provider,
        String providerUserId,
        UUID userId
) {
    public AuthIdentity(OAuthProvider provider, String userProviderId, UUID userId){
        this(UUID.randomUUID(), provider, userProviderId, userId);
    }
}
