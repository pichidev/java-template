package ar.com.pichidev.homestock.user.core.interfaces.repository;

import ar.com.pichidev.homestock.user.core.entity.User;

import java.util.Optional;

public interface GetUserByEmailRepository {
    Optional<User> execute(String email);
}
