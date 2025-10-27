package ar.com.pichidev.template.user.infraestructure.postgresql.repository;

import ar.com.pichidev.template.common.exception.RepositoryException;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.interfaces.repository.GetUserByEmailRepository;
import ar.com.pichidev.template.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.template.user.infraestructure.postgresql.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GetUserByEmailDbRepository implements GetUserByEmailRepository {
    private final UserJpaAdapter userJpaAdapter;
    private final UserMapper userMapper;

    @Override
    public Optional<User> execute(String email) {
        try{
            return userJpaAdapter.findByEmail(email).map(userMapper::toDomain);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
