package ar.com.pichidev.homestock.auth.core.usecase;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.core.entity.ProviderUserInformation;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.core.exception.NotImplementedOAuthStrategy;
import ar.com.pichidev.homestock.auth.core.interfaces.usecase.OAuthLoginStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotImplementedOAuthLoginStrategy implements OAuthLoginStrategy {

    @Override
    public OAuthProvider getProviderId() {
        return null;
    }

    @Override
    public ProviderUserInformation login(Map<String, Object> providerInformation) {
        throw new NotImplementedOAuthStrategy();
    }
}
