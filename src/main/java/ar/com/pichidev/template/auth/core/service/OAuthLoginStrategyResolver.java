package ar.com.pichidev.template.auth.core.service;

import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.core.exception.InvalidOAuthProviderId;
import ar.com.pichidev.template.auth.core.interfaces.usecase.OAuthLoginStrategy;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OAuthLoginStrategyResolver {
    private final Map<OAuthProvider, OAuthLoginStrategy> oauthStrategies;
    private final OAuthLoginStrategy notImplementedOAuthLoginStrategy;

    public OAuthLoginStrategyResolver(List<OAuthLoginStrategy> oauthStrategies, OAuthLoginStrategy notImplementedOAuthLoginStrategy) {
        this.notImplementedOAuthLoginStrategy = notImplementedOAuthLoginStrategy;
        this.oauthStrategies = toMap(oauthStrategies);
    }

    private Map<OAuthProvider, OAuthLoginStrategy> toMap(List<OAuthLoginStrategy> strategies){
        return strategies.stream()
                .filter(strategy-> strategy.getProviderId() != null)
                .collect(
                        Collectors.toMap(
                                OAuthLoginStrategy::getProviderId,
                                Function.identity(),
        (existingStrategy,newStrategy) -> newStrategy,
                                ()->new EnumMap<>(OAuthProvider.class)
                        )
                );
    }

    public OAuthLoginStrategy resolve(OAuthProvider providerId) {
        if(providerId == null){
            throw new InvalidOAuthProviderId();
        }

        return oauthStrategies.getOrDefault(providerId,notImplementedOAuthLoginStrategy);
    }
}
