package ar.com.pichidev.homestock.auth.configuration;

import ar.com.pichidev.piauth.core.service.TokenDeliveryStrategy;
import ar.com.pichidev.piauth.core.service.TokenGenerator;
import ar.com.pichidev.piauth.oauth.domain.service.OAuthRedirectDeliveryStrategy;
import ar.com.pichidev.piauth.oauth.domain.service.OAuthRedirectOnErrorStrategy;
import ar.com.pichidev.piauth.oauth.domain.service.OAuthUserResolver;
import ar.com.pichidev.piauth.oauth.module.OAuthModule;
import ar.com.pichidev.piauth.standard.oauth.entity.AuthorizationCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {
        @Bean
        public OAuthModule oauthModule(OAuthUserResolver resolver, TokenGenerator<AuthorizationCode> tokenGenerator, TokenDeliveryStrategy<AuthorizationCode> tokenDeliveryStrategy, OAuthRedirectDeliveryStrategy redirectStrategy, OAuthRedirectOnErrorStrategy redirectOnErrorStrategy) {
            return OAuthModule.Config
                    .tokenClass(AuthorizationCode.class)
                    .tokenGenerator(tokenGenerator)
                    .tokenDelivery(tokenDeliveryStrategy)
                    .oAuthUserResolver(resolver)
                    .redirectStrategy(redirectStrategy)
                    .redirectOnErrorStrategy(redirectOnErrorStrategy)
                    .build();
        }
}
