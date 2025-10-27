package ar.com.pichidev.template.auth.core.interfaces.integration;

import ar.com.pichidev.template.auth.core.entity.AuthUser;
import ar.com.pichidev.template.auth.core.entity.UserTokenInformation;

public interface CreateUserPort {
    UserTokenInformation execute(AuthUser authUser);
}
