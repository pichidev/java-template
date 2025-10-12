package ar.com.pichidev.homestock.auth.core.usecase;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.core.entity.ProviderUserInformation;
import ar.com.pichidev.homestock.auth.core.interfaces.usecase.OAuthLoginStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class GoogleOAuthLoginStrategy implements OAuthLoginStrategy {
    @Override
    public OAuthProvider getProviderId() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public ProviderUserInformation login(Map<String, Object> providerInformation) {
        return new ProviderUserInformation(
                (String) providerInformation.get("sub"),
                (String) providerInformation.get("email"),
                (String) providerInformation.get("given_name"),
                (String) providerInformation.get("family_name")
        );
    }
}
