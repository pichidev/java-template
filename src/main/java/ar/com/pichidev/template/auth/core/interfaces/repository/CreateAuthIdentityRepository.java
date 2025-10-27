package ar.com.pichidev.template.auth.core.interfaces.repository;

import ar.com.pichidev.template.auth.core.entity.AuthIdentity;

public interface CreateAuthIdentityRepository {
    void execute(AuthIdentity identity);
}
