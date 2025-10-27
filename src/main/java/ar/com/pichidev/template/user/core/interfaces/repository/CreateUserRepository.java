package ar.com.pichidev.template.user.core.interfaces.repository;

import ar.com.pichidev.template.user.core.entity.User;

public interface CreateUserRepository {
    void execute(User user);
}
