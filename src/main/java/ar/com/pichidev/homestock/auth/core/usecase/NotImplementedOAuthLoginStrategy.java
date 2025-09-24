package ar.com.pichidev.homestock.auth.core.usecase;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProviderId;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.core.exception.NotImplementedOAuthStrategy;
import ar.com.pichidev.homestock.auth.core.interfaces.usecase.OAuthLoginStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotImplementedOAuthLoginStrategy implements OAuthLoginStrategy {

    @Override
    public OAuthProviderId getProviderId() {
        return null;
    }

    @Override
    public UserTokenInformation login(Map<String, Object> providerInformation) {
        throw new NotImplementedOAuthStrategy();
    }
}
