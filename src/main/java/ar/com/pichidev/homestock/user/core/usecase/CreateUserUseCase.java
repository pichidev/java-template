package ar.com.pichidev.homestock.user.core.usecase;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.EmailAlreadyExistException;
import ar.com.pichidev.homestock.user.core.interfaces.repository.CreateUserRepository;
import ar.com.pichidev.homestock.user.core.interfaces.repository.GetUserByEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final CreateUserRepository createUserRepository;
    private final GetUserByEmailRepository getUserByEmailRepository;

    public User execute(User user) throws EmailAlreadyExistException {
        if(getUserByEmailRepository.execute(user.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
        }

        this.createUserRepository.execute(user);
        return user;
    }

    public void test() {
        System.out.println("Test method in CreateUserUseCase");
    }
}
