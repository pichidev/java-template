package ar.com.pichidev.template.auth.infrastructure.auth;

import ar.com.pichidev.template.auth.infrastructure.postgresql.adapter.AuthorizationCodeJpaAdapter;
import ar.com.pichidev.template.auth.infrastructure.postgresql.orm.AuthorizationCodeModel;
import ar.com.pichidev.piauth.standard.oauth.entity.AuthorizationCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationCodePostgresqlStoreTest {

    @Mock
    private AuthorizationCodeJpaAdapter jpaAdapter;

    @InjectMocks
    private AuthorizationCodePostgresqlStore store;

    @Test
    void saveMapsAndPersistsAuthorizationCode() {
        String code = "auth-code-123";
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("userId", "user-123");
        userPayload.put("email", "user@example.com");
        Instant expiresAt = Instant.now().plusSeconds(3600);

        AuthorizationCode authorizationCode = new AuthorizationCode(code, userPayload, expiresAt);

        store.save(authorizationCode);

        ArgumentCaptor<AuthorizationCodeModel> captor = ArgumentCaptor.forClass(AuthorizationCodeModel.class);
        verify(jpaAdapter).save(captor.capture());

        AuthorizationCodeModel savedModel = captor.getValue();
        assertEquals(code, savedModel.getCode());
        assertEquals(userPayload, savedModel.getUserPayload());
        assertEquals(expiresAt, savedModel.getExpiresAt());
    }

    @Test
    void removeMapsAndDeletesAuthorizationCode() {
        String code = "auth-code-456";
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("userId", "user-456");
        Instant expiresAt = Instant.now().plusSeconds(1800);

        AuthorizationCode authorizationCode = new AuthorizationCode(code, userPayload, expiresAt);

        store.remove(authorizationCode);

        ArgumentCaptor<AuthorizationCodeModel> captor = ArgumentCaptor.forClass(AuthorizationCodeModel.class);
        verify(jpaAdapter).delete(captor.capture());

        AuthorizationCodeModel deletedModel = captor.getValue();
        assertEquals(code, deletedModel.getCode());
        assertEquals(userPayload, deletedModel.getUserPayload());
        assertEquals(expiresAt, deletedModel.getExpiresAt());
    }

    @Test
    void getRetrievesAndMapsAuthorizationCode() {
        String code = "auth-code-789";
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("userId", "user-789");
        userPayload.put("roles", new String[]{"USER"});
        Instant expiresAt = Instant.now().plusSeconds(7200);

        AuthorizationCodeModel model = AuthorizationCodeModel.builder()
                .code(code)
                .userPayload(userPayload)
                .expiresAt(expiresAt)
                .build();

        when(jpaAdapter.getReferenceById(code)).thenReturn(model);

        AuthorizationCode result = store.get(code);

        assertNotNull(result);
        assertEquals(code, result.value());
        assertEquals(userPayload, result.userPayload());
        assertEquals(expiresAt, result.expiresAt());

        verify(jpaAdapter).getReferenceById(code);
    }
}