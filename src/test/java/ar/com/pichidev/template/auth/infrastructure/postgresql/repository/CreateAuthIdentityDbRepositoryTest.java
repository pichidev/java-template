package ar.com.pichidev.template.auth.infrastructure.postgresql.repository;

import ar.com.pichidev.template.auth.core.entity.AuthIdentity;
import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.infrastructure.postgresql.adapter.AuthIdentityJpaAdapter;
import ar.com.pichidev.template.auth.infrastructure.postgresql.orm.AuthIdentityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAuthIdentityDbRepositoryTest {

    @Mock
    private AuthIdentityJpaAdapter authIdentityJpaAdapter;

    @InjectMocks
    private CreateAuthIdentityDbRepository repository;

    @Test
    void executeMapsAndSavesAuthIdentity() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        AuthIdentity authIdentity = AuthIdentity.builder()
                .id(id)
                .provider(OAuthProvider.GOOGLE)
                .providerUserId("google-123")
                .userId(userId)
                .build();

        repository.execute(authIdentity);

        ArgumentCaptor<AuthIdentityModel> captor = ArgumentCaptor.forClass(AuthIdentityModel.class);
        verify(authIdentityJpaAdapter).save(captor.capture());

        AuthIdentityModel savedModel = captor.getValue();
        assertEquals(id, savedModel.getId());
        assertEquals(OAuthProvider.GOOGLE, savedModel.getProvider());
        assertEquals("google-123", savedModel.getProviderUserId());
        assertEquals(userId, savedModel.getUserId());
    }
}