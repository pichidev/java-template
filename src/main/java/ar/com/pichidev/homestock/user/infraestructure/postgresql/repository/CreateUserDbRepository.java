package ar.com.pichidev.homestock.user.infraestructure.postgresql.repository;

import ar.com.pichidev.homestock.common.exception.RepositoryException;
import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.interfaces.repository.CreateUserRepository;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreateUserDbRepository implements CreateUserRepository {
    private final UserJpaAdapter userJpaAdapter;
    private final UserMapper userMapper;

    @Override
    public void execute(User user) {
        try{
            this.userJpaAdapter.save(
                    userMapper.toModel(user)
            );
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
