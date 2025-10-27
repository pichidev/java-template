package ar.com.pichidev.template.auth.core.interfaces.integration;

import ar.com.pichidev.template.auth.core.entity.UserTokenInformation;

import java.util.UUID;

public interface GetUserInformationPort {
    UserTokenInformation byIdExecute(UUID userId);
    UserTokenInformation byEmailExecute(String email);
}
