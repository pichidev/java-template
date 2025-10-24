package ar.com.pichidev.homestock.auth.core.interfaces.repository;

import ar.com.pichidev.homestock.auth.core.entity.AuthIdentity;

public interface CreateAuthIdentityRepository {
    void execute(AuthIdentity identity);
}
