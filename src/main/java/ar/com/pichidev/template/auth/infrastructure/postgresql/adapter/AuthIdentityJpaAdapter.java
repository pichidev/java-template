package ar.com.pichidev.template.auth.infrastructure.postgresql.adapter;

import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.infrastructure.postgresql.orm.AuthIdentityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthIdentityJpaAdapter extends JpaRepository<AuthIdentityModel, UUID> {
    Optional<AuthIdentityModel> findByProviderAndProviderUserId(OAuthProvider provider, String providerUserId);
}
