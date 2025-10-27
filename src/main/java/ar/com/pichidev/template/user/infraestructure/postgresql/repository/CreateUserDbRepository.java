package ar.com.pichidev.template.user.infraestructure.postgresql.repository;

import ar.com.pichidev.template.common.exception.RepositoryException;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.interfaces.repository.CreateUserRepository;
import ar.com.pichidev.template.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.template.user.infraestructure.postgresql.mapper.UserMapper;
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
