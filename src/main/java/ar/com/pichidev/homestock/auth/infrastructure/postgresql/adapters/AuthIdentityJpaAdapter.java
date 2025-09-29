package ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapters;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthIdentityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthIdentityJpaAdapter extends JpaRepository<AuthIdentityModel, UUID> {
    Optional<AuthIdentityModel> findByProviderAndProviderUserId(OAuthProvider provider, String providerUserId);
}
