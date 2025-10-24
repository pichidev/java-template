package ar.com.pichidev.homestock.auth.core.interfaces.integration;

import ar.com.pichidev.homestock.auth.core.entity.AuthUser;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;

public interface CreateUserPort {
    UserTokenInformation execute(AuthUser authUser);
}
