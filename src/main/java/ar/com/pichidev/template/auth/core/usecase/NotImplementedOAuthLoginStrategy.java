package ar.com.pichidev.template.auth.core.usecase;

import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.core.entity.ProviderUserInformation;
import ar.com.pichidev.template.auth.core.exception.NotImplementedOAuthStrategy;
import ar.com.pichidev.template.auth.core.interfaces.usecase.OAuthLoginStrategy;
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
