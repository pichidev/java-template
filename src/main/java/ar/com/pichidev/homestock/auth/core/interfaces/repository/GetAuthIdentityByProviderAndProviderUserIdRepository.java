package ar.com.pichidev.homestock.auth.core.interfaces.repository;

import ar.com.pichidev.homestock.auth.core.entity.AuthIdentity;
import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;

import java.util.Optional;

public interface GetAuthIdentityByProviderAndProviderUserIdRepository {
    Optional<AuthIdentity> execute(OAuthProvider provider, String userProviderId);
}
