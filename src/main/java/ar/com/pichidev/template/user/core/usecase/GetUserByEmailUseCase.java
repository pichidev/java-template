package ar.com.pichidev.template.user.core.usecase;

import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.exception.UserNotFoundException;
import ar.com.pichidev.template.user.core.interfaces.repository.GetUserByEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByEmailUseCase {
    private final GetUserByEmailRepository getUserByEmailRepository;

    public User execute(String email) throws UserNotFoundException {
        return getUserByEmailRepository.execute(email).orElseThrow(
                UserNotFoundException::new
        );
    }
}
