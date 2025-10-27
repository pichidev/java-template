package ar.com.pichidev.template.user.core.interfaces.repository;

import ar.com.pichidev.template.user.core.entity.User;

import java.util.Optional;

public interface GetUserByEmailRepository {
    Optional<User> execute(String email);
}
