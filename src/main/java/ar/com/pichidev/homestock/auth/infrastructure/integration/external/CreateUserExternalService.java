package ar.com.pichidev.homestock.auth.infrastructure.integration.external;

import ar.com.pichidev.homestock.auth.core.entity.AuthUser;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.CreateUserPort;
import org.springframework.stereotype.Component;

@Component
public class CreateUserExternalService implements CreateUserPort {
    @Override
    public UserTokenInformation execute(AuthUser authUser) {
        return null;
    }
}
