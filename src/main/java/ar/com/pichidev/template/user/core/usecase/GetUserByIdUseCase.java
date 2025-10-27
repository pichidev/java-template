package ar.com.pichidev.template.user.core.usecase;

import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.exception.UserNotFoundException;
import ar.com.pichidev.template.user.core.interfaces.repository.GetUserByIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {
    private final GetUserByIdRepository getUserByIdRepository;

    public User execute(UUID id) {
        return getUserByIdRepository.execute(id).orElseThrow(
                UserNotFoundException::new
        );
    }
}
