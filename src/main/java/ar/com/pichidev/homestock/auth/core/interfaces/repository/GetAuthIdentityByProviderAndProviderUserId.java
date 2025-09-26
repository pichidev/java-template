package ar.com.pichidev.homestock.auth.core.interfaces.repository;

import ar.com.pichidev.homestock.auth.core.entity.AuthIdentity;
import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;

import java.util.UUID;

public interface GetAuthIdentityByProviderAndProviderUserId {
    AuthIdentity execute(OAuthProvider provider, String userProviderId);
}
