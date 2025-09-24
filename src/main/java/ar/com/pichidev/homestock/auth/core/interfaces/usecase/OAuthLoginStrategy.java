package ar.com.pichidev.homestock.auth.core.interfaces.usecase;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProviderId;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;

import java.util.Map;

public interface OAuthLoginStrategy {

    OAuthProviderId getProviderId();
    UserTokenInformation login(Map<String, Object> providerInformation);
}
