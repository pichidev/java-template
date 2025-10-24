package ar.com.pichidev.homestock.auth.core.interfaces.integration;

import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;

import java.util.UUID;

public interface GetUserInformationPort {
    UserTokenInformation byIdExecute(UUID userId);
    UserTokenInformation byEmailExecute(String email);
}
