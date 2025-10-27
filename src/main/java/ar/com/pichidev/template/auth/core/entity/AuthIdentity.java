package ar.com.pichidev.template.auth.core.entity;

import lombok.Builder;

import java.util.UUID;

@Builder
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
