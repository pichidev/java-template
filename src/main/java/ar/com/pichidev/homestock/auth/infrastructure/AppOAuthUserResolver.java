package ar.com.pichidev.homestock.auth.infrastructure;

import ar.com.pichidev.piauth.core.service.TokenPayload;
import ar.com.pichidev.piauth.oauth.domain.service.OAuthUserResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("oauthUserResolver")
public class AppOAuthUserResolver implements OAuthUserResolver {
    @Override
    public TokenPayload resolveUserFromProvider(String provider, Map<String, Object> providerUserInfo) {
        return new TokenPayload
                .Builder()
                .add("id","123")
                .add("email","agca@gmail.com")
                .add("roles", new String[]{"Maria", "Juan"})
                .build();
    }
}
