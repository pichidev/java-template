package ar.com.pichidev.template.user.core.usecase;

import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.exception.EmailAlreadyExistException;
import ar.com.pichidev.template.user.core.interfaces.repository.CreateUserRepository;
import ar.com.pichidev.template.user.core.interfaces.repository.GetUserByEmailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private CreateUserRepository createUserRepository;
    @Mock
    private GetUserByEmailRepository getUserByEmailRepository;
    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void executeCreatesUserWhenEmailDoesNotExist() throws EmailAlreadyExistException {
        User user = User.builder()
                .email("test@example.com")
                .name("Test")
                .lastName("User")
                .build();
        when(getUserByEmailRepository.execute(user.getEmail())).thenReturn(Optional.empty());

        User result = createUserUseCase.execute(user);

        assertEquals(user, result);
        verify(createUserRepository, times(1)).execute(user);
    }

    @Test
    void executeThrowsExceptionWhenEmailAlreadyExists() {
        User user = User.builder()
                .email("test@example.com")
                .name("Test")
                .lastName("User")
                .build();
        when(getUserByEmailRepository.execute(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistException.class, () -> createUserUseCase.execute(user));
        verify(createUserRepository, never()).execute(any());
    }
}
