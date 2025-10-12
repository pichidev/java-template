package ar.com.pichidev.homestock.user.core.usecase;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.UserNotFoundException;
import ar.com.pichidev.homestock.user.core.interfaces.repository.GetUserByEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByEmailUseCase {
    private final GetUserByEmailRepository getUserByEmailRepository;

    public User execute(String email) {
        return getUserByEmailRepository.execute(email).orElseThrow(
                UserNotFoundException::new
        );
    }
}
