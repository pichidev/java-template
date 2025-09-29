package ar.com.pichidev.homestock.auth.infrastructure.postgresql.repository;

import ar.com.pichidev.homestock.auth.core.entity.AuthIdentity;
import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.core.interfaces.repository.GetAuthIdentityByProviderAndProviderUserIdRepository;
import ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapters.AuthIdentityJpaAdapter;
import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthIdentityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GetAuthIdentityByProviderAndProviderUserIdDbRepository implements GetAuthIdentityByProviderAndProviderUserIdRepository {
    private final AuthIdentityJpaAdapter authIdentityJpaAdapter;

    private AuthIdentity toDomain(AuthIdentityModel model) {
        return AuthIdentity.builder()
                .providerUserId(model.getProviderUserId())
                .userId(model.getUserId())
                .id(model.getId())
                .provider(model.getProvider())
                .build();
    }

    @Override
    public Optional<AuthIdentity> execute(OAuthProvider provider, String userProviderId) {
        return authIdentityJpaAdapter
                .findByProviderAndProviderUserId(provider, userProviderId)
                .map(this::toDomain);
    }
}
