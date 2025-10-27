package ar.com.pichidev.template.auth.infrastructure.auth;

import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.template.auth.core.service.OAuthLoginOrchestrator;
import ar.com.pichidev.piauth.core.service.TokenPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppOAuthUserResolverTest {

    @Mock
    private OAuthLoginOrchestrator oAuthLoginOrchestrator;

    @InjectMocks
    private AppOAuthUserResolver resolver;

    @Test
    void resolveUserFromProviderReturnsTokenPayload() {
        String provider = "google";
        Map<String, Object> providerUserInfo = new HashMap<>();
        providerUserInfo.put("sub", "google-123");
        providerUserInfo.put("email", "user@example.com");

        UUID userId = UUID.randomUUID();
        UserTokenInformation userTokenInfo = UserTokenInformation.builder()
                .id(userId)
                .email("user@example.com")
                .name("John")
                .lastname("Doe")
                .roles(new String[]{"USER", "ADMIN"})
                .build();

        when(oAuthLoginOrchestrator.execute(OAuthProvider.GOOGLE, providerUserInfo))
                .thenReturn(userTokenInfo);

        TokenPayload result = resolver.resolveUserFromProvider(provider, providerUserInfo);

        assertNotNull(result);
        Map<String, Object> payload = result.toMap();
        assertEquals(userId.toString(), payload.get("id"));
        assertEquals("John", payload.get("name"));
        assertEquals("Doe", payload.get("lastname"));
        assertEquals("user@example.com", payload.get("email"));
        assertArrayEquals(new String[]{"USER", "ADMIN"}, (String[]) payload.get("roles"));

        verify(oAuthLoginOrchestrator).execute(OAuthProvider.GOOGLE, providerUserInfo);
    }

    @Test
    void resolveUserFromProviderConvertsProviderToUpperCase() {
        String provider = "GoOgLe";
        Map<String, Object> providerUserInfo = new HashMap<>();

        UUID userId = UUID.randomUUID();
        UserTokenInformation userTokenInfo = UserTokenInformation.builder()
                .id(userId)
                .email("test@example.com")
                .name("Test")
                .lastname("User")
                .roles(new String[]{"USER"})
                .build();

        when(oAuthLoginOrchestrator.execute(OAuthProvider.GOOGLE, providerUserInfo))
                .thenReturn(userTokenInfo);

        TokenPayload result = resolver.resolveUserFromProvider(provider, providerUserInfo);

        assertNotNull(result);
        verify(oAuthLoginOrchestrator).execute(OAuthProvider.GOOGLE, providerUserInfo);
    }
}