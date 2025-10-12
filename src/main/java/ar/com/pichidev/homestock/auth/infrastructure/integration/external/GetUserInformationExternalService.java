package ar.com.pichidev.homestock.auth.infrastructure.integration.external;

import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.GetUserInformationPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetUserInformationExternalService implements GetUserInformationPort {
    @Override
    public UserTokenInformation byIdExecute(UUID userId) {
        return null;
    }

    @Override
    public UserTokenInformation byEmailExecute(String email) {
        return null;
    }
}
