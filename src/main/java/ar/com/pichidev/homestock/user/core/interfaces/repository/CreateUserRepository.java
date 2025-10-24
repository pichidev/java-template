package ar.com.pichidev.homestock.user.core.interfaces.repository;

import ar.com.pichidev.homestock.user.core.entity.User;

public interface CreateUserRepository {
    void execute(User user);
}
