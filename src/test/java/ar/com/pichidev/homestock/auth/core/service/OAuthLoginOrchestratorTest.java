package ar.com.pichidev.homestock.auth.core.service;

import ar.com.pichidev.homestock.auth.core.entity.*;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.CreateUserPort;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.GetUserInformationPort;
import ar.com.pichidev.homestock.auth.core.interfaces.repository.CreateAuthIdentityRepository;
import ar.com.pichidev.homestock.auth.core.interfaces.repository.GetAuthIdentityByProviderAndProviderUserIdRepository;
import ar.com.pichidev.homestock.auth.core.interfaces.usecase.OAuthLoginStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthLoginOrchestratorTest {

    @Mock
    private OAuthLoginStrategyResolver oauthLoginStrategyResolver;
    @Mock
    private CreateUserPort createUserPort;
    @Mock
    private GetUserInformationPort getUserInformationPort;
    @Mock
    private CreateAuthIdentityRepository createAuthIdentityRepository;
    @Mock
    private GetAuthIdentityByProviderAndProviderUserIdRepository getAuthIdentityByProviderAndProviderUserIdRepository;
    @Mock
    private OAuthLoginStrategy oauthLoginStrategy;

    @InjectMocks
    private OAuthLoginOrchestrator orchestrator;

    @Test
    void executeReturnsUserWhenAuthIdentityExists() {
        OAuthProvider provider = OAuthProvider.GOOGLE;
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-123");
        providerInfo.put("email", "user@example.com");
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ProviderUserInformation providerUserInfo = new ProviderUserInformation(
                "google-123",
                "user@example.com",
                "John",
                "Doe"
        );

        UUID userId = UUID.randomUUID();
        AuthIdentity existingAuthIdentity = AuthIdentity.builder()
                .id(UUID.randomUUID())
                .provider(provider)
                .providerUserId("google-123")
                .userId(userId)
                .build();

        UserTokenInformation expectedUser = UserTokenInformation.builder()
                .id(userId)
                .email("user@example.com")
                .name("John")
                .lastname("Doe")
                .roles(new String[]{"USER"})
                .build();

        when(oauthLoginStrategyResolver.resolve(provider)).thenReturn(oauthLoginStrategy);
        when(oauthLoginStrategy.login(providerInfo)).thenReturn(providerUserInfo);
        when(getAuthIdentityByProviderAndProviderUserIdRepository.execute(provider, "google-123"))
                .thenReturn(Optional.of(existingAuthIdentity));
        when(getUserInformationPort.byIdExecute(userId)).thenReturn(expectedUser);

        UserTokenInformation result = orchestrator.execute(provider, providerInfo);

        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(oauthLoginStrategyResolver).resolve(provider);
        verify(oauthLoginStrategy).login(providerInfo);
        verify(getAuthIdentityByProviderAndProviderUserIdRepository).execute(provider, "google-123");
        verify(getUserInformationPort).byIdExecute(userId);
        verify(getUserInformationPort, never()).byEmailExecute(anyString());
        verify(createAuthIdentityRepository, never()).execute(any());
        verify(createUserPort, never()).execute(any());
    }

    @Test
    void executeCreatesAuthIdentityWhenUserExistsByEmail() {
        OAuthProvider provider = OAuthProvider.GOOGLE;
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-123");
        providerInfo.put("email", "user@example.com");
        providerInfo.put("given_name", "John");
        providerInfo.put("family_name", "Doe");

        ProviderUserInformation providerUserInfo = new ProviderUserInformation(
                "google-123",
                "user@example.com",
                "John",
                "Doe"
        );

        UUID existingUserId = UUID.randomUUID();
        UserTokenInformation existingUser = UserTokenInformation.builder()
                .id(existingUserId)
                .email("user@example.com")
                .name("John")
                .lastname("Doe")
                .roles(new String[]{"USER"})
                .build();

        when(oauthLoginStrategyResolver.resolve(provider)).thenReturn(oauthLoginStrategy);
        when(oauthLoginStrategy.login(providerInfo)).thenReturn(providerUserInfo);
        when(getAuthIdentityByProviderAndProviderUserIdRepository.execute(provider, "google-123"))
                .thenReturn(Optional.empty());
        when(getUserInformationPort.byEmailExecute("user@example.com")).thenReturn(existingUser);

        UserTokenInformation result = orchestrator.execute(provider, providerInfo);

        assertNotNull(result);
        assertEquals(existingUser, result);

        ArgumentCaptor<AuthIdentity> authIdentityCaptor = ArgumentCaptor.forClass(AuthIdentity.class);
        verify(createAuthIdentityRepository).execute(authIdentityCaptor.capture());

        AuthIdentity capturedAuthIdentity = authIdentityCaptor.getValue();
        assertEquals(provider, capturedAuthIdentity.provider());
        assertEquals("google-123", capturedAuthIdentity.providerUserId());
        assertEquals(existingUserId, capturedAuthIdentity.userId());

        verify(createUserPort, never()).execute(any());
    }

    @Test
    void executeCreatesNewUserWhenNoAuthIdentityOrUserExists() {
        OAuthProvider provider = OAuthProvider.GOOGLE;
        Map<String, Object> providerInfo = new HashMap<>();
        providerInfo.put("sub", "google-123");
        providerInfo.put("email", "newuser@example.com");
        providerInfo.put("given_name", "Jane");
        providerInfo.put("family_name", "Smith");

        ProviderUserInformation providerUserInfo = new ProviderUserInformation(
                "google-123",
                "newuser@example.com",
                "Jane",
                "Smith"
        );

        UserTokenInformation newUserTokenInfo = UserTokenInformation.builder()
                .id(UUID.randomUUID())
                .email("newuser@example.com")
                .name("Jane")
                .lastname("Smith")
                .roles(new String[]{"USER"})
                .build();

        when(oauthLoginStrategyResolver.resolve(provider)).thenReturn(oauthLoginStrategy);
        when(oauthLoginStrategy.login(providerInfo)).thenReturn(providerUserInfo);
        when(getAuthIdentityByProviderAndProviderUserIdRepository.execute(provider, "google-123"))
                .thenReturn(Optional.empty());
        when(getUserInformationPort.byEmailExecute("newuser@example.com")).thenReturn(null);
        when(createUserPort.execute(any(AuthUser.class))).thenReturn(newUserTokenInfo);

        UserTokenInformation result = orchestrator.execute(provider, providerInfo);

        assertNotNull(result);
        assertEquals(newUserTokenInfo, result);

        ArgumentCaptor<AuthUser> authUserCaptor = ArgumentCaptor.forClass(AuthUser.class);
        verify(createUserPort).execute(authUserCaptor.capture());

        AuthUser capturedAuthUser = authUserCaptor.getValue();
        assertEquals("newuser@example.com", capturedAuthUser.email());
        assertEquals("Jane", capturedAuthUser.name());
        assertEquals("Smith", capturedAuthUser.lastname());
        assertNotNull(capturedAuthUser.id());

        ArgumentCaptor<AuthIdentity> authIdentityCaptor = ArgumentCaptor.forClass(AuthIdentity.class);
        verify(createAuthIdentityRepository).execute(authIdentityCaptor.capture());

        AuthIdentity capturedAuthIdentity = authIdentityCaptor.getValue();
        assertEquals(provider, capturedAuthIdentity.provider());
        assertEquals("google-123", capturedAuthIdentity.providerUserId());
        assertEquals(capturedAuthUser.id(), capturedAuthIdentity.userId());
    }
}