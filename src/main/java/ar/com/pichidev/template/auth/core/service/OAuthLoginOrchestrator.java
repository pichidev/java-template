package ar.com.pichidev.template.auth.core.service;

import ar.com.pichidev.template.auth.core.entity.*;
import ar.com.pichidev.template.auth.core.interfaces.integration.CreateUserPort;
import ar.com.pichidev.template.auth.core.interfaces.integration.GetUserInformationPort;
import ar.com.pichidev.template.auth.core.interfaces.repository.CreateAuthIdentityRepository;
import ar.com.pichidev.template.auth.core.interfaces.repository.GetAuthIdentityByProviderAndProviderUserIdRepository;
import ar.com.pichidev.template.auth.core.interfaces.usecase.OAuthLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthLoginOrchestrator {
    private final OAuthLoginStrategyResolver oauthLoginStrategyResolver;
    private final CreateUserPort createUserPort;
    private final GetUserInformationPort getUserInformationPort;
    private final CreateAuthIdentityRepository createAuthIdentityRepository;
    private final GetAuthIdentityByProviderAndProviderUserIdRepository getAuthIdentityByProviderAndProviderUserIdRepository;

    public UserTokenInformation execute(OAuthProvider provider, Map<String, Object> providerInformation){
        OAuthLoginStrategy strategy = this.oauthLoginStrategyResolver.resolve(provider);
        ProviderUserInformation providerUserInformation = strategy.login(providerInformation);

        Optional<AuthIdentity> authIdentity = this.getAuthIdentityByProviderAndProviderUserIdRepository.execute(provider,providerUserInformation.userId());

        if(authIdentity.isPresent()){
            return this.getUserInformationPort.byIdExecute(authIdentity.get().userId());
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
