package ar.com.pichidev.template.user.core.usecase;

import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.exception.EmailAlreadyExistException;
import ar.com.pichidev.template.user.core.interfaces.repository.CreateUserRepository;
import ar.com.pichidev.template.user.core.interfaces.repository.GetUserByEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final CreateUserRepository createUserRepository;
    private final GetUserByEmailRepository getUserByEmailRepository;

    public User execute(User user) throws EmailAlreadyExistException {
        if(getUserByEmailRepository.execute(user.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
        }
        user.assignRoles(Set.of(Roles.USER));

        this.createUserRepository.execute(user);
        return user;
    }
}
