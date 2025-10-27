package ar.com.pichidev.template.auth.core.interfaces.usecase;

import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.core.entity.ProviderUserInformation;

import java.util.Map;

public interface OAuthLoginStrategy {

    OAuthProvider getProviderId();
    ProviderUserInformation login(Map<String, Object> providerInformation);
}
