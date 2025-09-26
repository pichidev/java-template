package ar.com.pichidev.homestock.auth.infrastructure;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
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
        UserTokenInformation tokenInformation =oAuthLoginOrchestrator.execute(OAuthProvider.valueOf(provider.toUpperCase()), providerUserInfo);

        return new TokenPayload
                .Builder()
                .add("id", tokenInformation.id().toString())
                .add("name", tokenInformation.name())
                .add("lastname", tokenInformation.lastname())
                .add("email", tokenInformation.email())
                .add("roles", tokenInformation.roles())
                .build();
    }
}
