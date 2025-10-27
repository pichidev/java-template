package ar.com.pichidev.template.auth.infrastructure.postgresql.repository;

import ar.com.pichidev.template.auth.core.entity.AuthIdentity;
import ar.com.pichidev.template.auth.core.interfaces.repository.CreateAuthIdentityRepository;
import ar.com.pichidev.template.auth.infrastructure.postgresql.adapter.AuthIdentityJpaAdapter;
import ar.com.pichidev.template.auth.infrastructure.postgresql.orm.AuthIdentityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreateAuthIdentityDbRepository implements CreateAuthIdentityRepository {
    private final AuthIdentityJpaAdapter authIdentityJpaAdapter;

    private AuthIdentityModel toModel(AuthIdentity authIdentity) {
        return AuthIdentityModel.builder()
                .id(authIdentity.id())
                .provider(authIdentity.provider())
                .providerUserId(authIdentity.providerUserId())
                .userId(authIdentity.userId())
                .build();
    }

    @Override
    public void execute(AuthIdentity identity) {
        authIdentityJpaAdapter.save(this.toModel(identity));
    }
}
