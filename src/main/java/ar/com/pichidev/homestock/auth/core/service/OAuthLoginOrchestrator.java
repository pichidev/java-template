package ar.com.pichidev.homestock.auth.core.service;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProviderId;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.core.interfaces.usecase.OAuthLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthLoginOrchestrator {
    private final OAuthLoginStrategyResolver oauthLoginStrategyResolver;

    public UserTokenInformation execute(OAuthProviderId provider, Map<String, Object> providerInformation){
        OAuthLoginStrategy strategy = this.oauthLoginStrategyResolver.resolve(provider);
        return strategy.login(providerInformation);
    }
}
