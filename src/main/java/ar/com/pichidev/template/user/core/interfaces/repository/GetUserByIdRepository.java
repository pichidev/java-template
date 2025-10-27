package ar.com.pichidev.template.user.core.interfaces.repository;

import ar.com.pichidev.template.user.core.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserByIdRepository {
    Optional<User> execute(UUID id);
}
