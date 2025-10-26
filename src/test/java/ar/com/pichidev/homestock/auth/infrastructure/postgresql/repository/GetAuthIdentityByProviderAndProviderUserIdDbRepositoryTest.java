package ar.com.pichidev.homestock.auth.infrastructure.postgresql.repository;

import ar.com.pichidev.homestock.auth.core.entity.AuthIdentity;
import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapter.AuthIdentityJpaAdapter;
import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthIdentityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAuthIdentityByProviderAndProviderUserIdDbRepositoryTest {

    @Mock
    private AuthIdentityJpaAdapter authIdentityJpaAdapter;

    @InjectMocks
    private GetAuthIdentityByProviderAndProviderUserIdDbRepository repository;

    @Test
    void executeReturnsAuthIdentityWhenFound() {
        OAuthProvider provider = OAuthProvider.GOOGLE;
        String providerUserId = "google-123";
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        AuthIdentityModel model = AuthIdentityModel.builder()
                .id(id)
                .provider(provider)
                .providerUserId(providerUserId)
                .userId(userId)
                .build();

        when(authIdentityJpaAdapter.findByProviderAndProviderUserId(provider, providerUserId))
                .thenReturn(Optional.of(model));

        Optional<AuthIdentity> result = repository.execute(provider, providerUserId);

        assertTrue(result.isPresent());
        AuthIdentity authIdentity = result.get();
        assertEquals(id, authIdentity.id());
        assertEquals(provider, authIdentity.provider());
        assertEquals(providerUserId, authIdentity.providerUserId());
        assertEquals(userId, authIdentity.userId());

        verify(authIdentityJpaAdapter).findByProviderAndProviderUserId(provider, providerUserId);
    }

    @Test
    void executeReturnsEmptyWhenNotFound() {
        OAuthProvider provider = OAuthProvider.GOOGLE;
        String providerUserId = "google-456";

        when(authIdentityJpaAdapter.findByProviderAndProviderUserId(provider, providerUserId))
                .thenReturn(Optional.empty());

        Optional<AuthIdentity> result = repository.execute(provider, providerUserId);

        assertTrue(result.isEmpty());
        verify(authIdentityJpaAdapter).findByProviderAndProviderUserId(provider, providerUserId);
    }

}