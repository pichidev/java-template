package ar.com.pichidev.homestock.user.core.usecase;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.UserNotFoundException;
import ar.com.pichidev.homestock.user.core.interfaces.repository.GetUserByEmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailUseCaseTest {
    @Mock
    private GetUserByEmailRepository getUserByEmailRepository;
    @InjectMocks
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @Test
    void returnsUserWhenEmailExists() throws UserNotFoundException {
        User user = User.builder()
                .email("test@example.com")
                .name("Test")
                .lastName("User")
                .build();
        when(getUserByEmailRepository.execute(user.getEmail())).thenReturn(Optional.of(user));

        User result = getUserByEmailUseCase.execute(user.getEmail());

        assertEquals(user, result);
        verify(getUserByEmailRepository, times(1)).execute(user.getEmail());
    }

    @Test
    void throwsUserNotFoundExceptionWhenEmailDoesNotExist() {
        String email = "nonexistent@example.com";
        when(getUserByEmailRepository.execute(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> getUserByEmailUseCase.execute(email));
        verify(getUserByEmailRepository, times(1)).execute(email);
    }
}
