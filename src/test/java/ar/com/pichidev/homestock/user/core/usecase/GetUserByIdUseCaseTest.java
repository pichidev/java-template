package ar.com.pichidev.homestock.user.core.usecase;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.UserNotFoundException;
import ar.com.pichidev.homestock.user.core.interfaces.repository.GetUserByIdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseTest {

    @Mock
    private GetUserByIdRepository getUserByIdRepository;

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    @Test
    void returnsUserWhenIdExists() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .email("test@example.com")
                .name("Test")
                .lastName("User")
                .build();
        when(getUserByIdRepository.execute(id)).thenReturn(Optional.of(user));

        User result = getUserByIdUseCase.execute(id);

        assertEquals(user, result);
        verify(getUserByIdRepository, times(1)).execute(id);
    }

    @Test
    void throwsUserNotFoundExceptionWhenIdDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(getUserByIdRepository.execute(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> getUserByIdUseCase.execute(id));
        verify(getUserByIdRepository, times(1)).execute(id);
    }
}
