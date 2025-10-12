package ar.com.pichidev.homestock.user.core.usecase;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.interfaces.repository.CreateUserRepository;
import ar.com.pichidev.homestock.user.core.interfaces.repository.GetUserByEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final CreateUserRepository createUserRepository;
    private final GetUserByEmailRepository getUserByEmailRepository;

    public void execute(User user) {
        if(getUserByEmailRepository.execute(user.getEmail()).isEmpty()){
            throw new
        }

        this.createUserRepository.execute(user);
    }
}
