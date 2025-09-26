package ar.com.pichidev.homestock.auth.core.service;

import ar.com.pichidev.homestock.auth.core.entity.*;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.CreateUserPort;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.GetUserInformationPort;
import ar.com.pichidev.homestock.auth.core.interfaces.repository.CreateAuthIdentityRepository;
import ar.com.pichidev.homestock.auth.core.interfaces.repository.GetAuthIdentityByProviderAndProviderUserId;
import ar.com.pichidev.homestock.auth.core.interfaces.usecase.OAuthLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthLoginOrchestrator {
    private final OAuthLoginStrategyResolver oauthLoginStrategyResolver;
    private final CreateUserPort createUserPort;
    private final GetUserInformationPort getUserInformationPort;
    private final CreateAuthIdentityRepository createAuthIdentityRepository;
    private final GetAuthIdentityByProviderAndProviderUserId getAuthIdentityByProviderAndProviderUserId;

    public UserTokenInformation execute(OAuthProvider provider, Map<String, Object> providerInformation){
        OAuthLoginStrategy strategy = this.oauthLoginStrategyResolver.resolve(provider);
        ProviderUserInformation providerUserInformation = strategy.login(providerInformation);

        AuthIdentity authIdentity = this.getAuthIdentityByProviderAndProviderUserId.execute(provider,providerUserInformation.userId());

        if(authIdentity != null){
            return this.getUserInformationPort.byIdExecute(authIdentity.userId());
        }

        // SI es nula veo si ya existe un usuario con ese email
        UserTokenInformation existingUserTokenInformation = this.getUserInformationPort.byEmailExecute(providerUserInformation.email());
        if(existingUserTokenInformation != null){
            this.createAuthIdentityRepository.execute(
                    new AuthIdentity(
                            provider,
                            providerUserInformation.userId(),
                            existingUserTokenInformation.id()
            ));
            return existingUserTokenInformation;
        }

        AuthUser authUser = new AuthUser(
                providerUserInformation.email(),
                providerUserInformation.name(),
                providerUserInformation.lastname()
        );

        this.createAuthIdentityRepository.execute(
                new AuthIdentity(
                        provider,
                        providerUserInformation.userId(),
                        authUser.id()
                ));

        return this.createUserPort.execute(authUser);
    }
}
