package ar.com.pichidev.homestock.auth.infrastructure.auth;

import ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapters.AuthorizationCodeJpaAdapter;
import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthorizationCodeModel;
import ar.com.pichidev.piauth.standard.oauth.entity.AuthorizationCode;
import ar.com.pichidev.piauth.standard.oauth.services.AuthorizationCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthorizationCodePostgresqlStore implements AuthorizationCodeStore {
    private final AuthorizationCodeJpaAdapter  jpaAdapter;

    private AuthorizationCodeModel mapToModel(AuthorizationCode authorizationCode) {
        return AuthorizationCodeModel.builder()
                .value(authorizationCode.value())
                .userPayload(authorizationCode.userPayload())
                .expiresAt(authorizationCode.expiresAt())
                .build();
    };

    private AuthorizationCode mapToAuthorizationCode(AuthorizationCodeModel authorizationCodeModel) {
        return new AuthorizationCode(
                authorizationCodeModel.getValue(),
                authorizationCodeModel.getUserPayload(),
                authorizationCodeModel.getExpiresAt()
        );
    }

    @Override
    public void save(AuthorizationCode authorizationCode) {
        jpaAdapter.save(
                mapToModel(authorizationCode)
        );
    }

    @Override
    public void remove(AuthorizationCode authorizationCode) {
        jpaAdapter.delete(
                mapToModel(authorizationCode)
        );
    }

    @Override
    public AuthorizationCode get(String code) {
        AuthorizationCodeModel authCode = jpaAdapter.getReferenceById(code);
        return mapToAuthorizationCode(authCode);
    }
}
