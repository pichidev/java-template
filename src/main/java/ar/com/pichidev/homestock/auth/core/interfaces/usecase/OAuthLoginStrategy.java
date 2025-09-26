package ar.com.pichidev.homestock.auth.core.interfaces.usecase;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import ar.com.pichidev.homestock.auth.core.entity.ProviderUserInformation;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;

import java.util.Map;

public interface OAuthLoginStrategy {

    OAuthProvider getProviderId();
    ProviderUserInformation login(Map<String, Object> providerInformation);
}
