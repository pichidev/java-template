package ar.com.pichidev.homestock.user.infraestructure.postgresql.repository;

import ar.com.pichidev.homestock.common.exception.RepositoryException;
import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.interfaces.repository.GetUserByIdRepository;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GetUserByIdDbRepository implements GetUserByIdRepository {
    private final UserJpaAdapter userJpaAdapter;
    private final UserMapper userMapper;

    @Override
    public Optional<User> execute(UUID id) {
        try {
            return this.userJpaAdapter.findById(id).map(userMapper::toDomain);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
