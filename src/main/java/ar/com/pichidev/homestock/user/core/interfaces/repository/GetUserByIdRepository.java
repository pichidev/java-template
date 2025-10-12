package ar.com.pichidev.homestock.user.core.interfaces.repository;

import java.util.UUID;

public interface GetUserByIdRepository {
    void execute(UUID id);
}
