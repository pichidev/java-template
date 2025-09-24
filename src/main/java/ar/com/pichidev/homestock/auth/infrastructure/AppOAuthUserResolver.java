package ar.com.pichidev.homestock.auth.infrastructure;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProviderId;
import ar.com.pichidev.homestock.auth.core.service.OAuthLoginOrchestrator;
import ar.com.pichidev.piauth.core.service.TokenPayload;
import ar.com.pichidev.piauth.oauth.domain.service.OAuthUserResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("oauthUserResolver")
@RequiredArgsConstructor
public class AppOAuthUserResolver implements OAuthUserResolver {
    private final OAuthLoginOrchestrator oAuthLoginOrchestrator;

    @Override
    public TokenPayload resolveUserFromProvider(String provider, Map<String, Object> providerUserInfo) {
        oAuthLoginOrchestrator.execute(OAuthProviderId.valueOf(provider), providerUserInfo);

        return new TokenPayload
                .Builder()
                .add("id","123")
                .add("email","agca@gmail.com")
                .add("roles", new String[]{"Maria", "Juan"})
                .build();
    }
}
