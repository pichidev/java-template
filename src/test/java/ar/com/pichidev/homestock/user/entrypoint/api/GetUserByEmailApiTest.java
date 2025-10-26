package ar.com.pichidev.homestock.user.entrypoint.api;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.UserNotFoundException;
import ar.com.pichidev.homestock.user.core.usecase.GetUserByEmailUseCase;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.mapper.UserApiMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailApiTest {

    @Mock
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @Mock
    private UserApiMapper userApiMapper;

    @InjectMocks
    private GetUserByEmailApi getUserByEmailApi;

    @Test
    void returnsUserOutputDtoWhenEmailExists() throws UserNotFoundException {
        String email = "test@example.com";
        User user = User.builder()
                .name("Test")
                .lastName("User")
                .email(email)
                .build();

        UserOutputDto outputDto = UserOutputDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(Set.of("USER"))
                .build();

        when(getUserByEmailUseCase.execute(email)).thenReturn(user);
        when(userApiMapper.toDto(user)).thenReturn(outputDto);

        UserOutputDto result = getUserByEmailApi.execute(email);

        assertEquals(outputDto, result);
        verify(getUserByEmailUseCase, times(1)).execute(email);
        verify(userApiMapper, times(1)).toDto(user);
    }

    @Test
    void throwsUserNotFoundExceptionWhenEmailDoesNotExist() throws UserNotFoundException {
        String email = "nonexistent@example.com";

        when(getUserByEmailUseCase.execute(email)).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> getUserByEmailApi.execute(email));
        verify(getUserByEmailUseCase, times(1)).execute(email);
        verify(userApiMapper, never()).toDto(any());
    }

}
