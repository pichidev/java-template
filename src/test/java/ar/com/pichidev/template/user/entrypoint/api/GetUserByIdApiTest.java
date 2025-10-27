// java
package ar.com.pichidev.template.user.entrypoint.api;

import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.exception.UserNotFoundException;
import ar.com.pichidev.template.user.core.usecase.GetUserByIdUseCase;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
import ar.com.pichidev.template.user.entrypoint.api.mapper.UserApiMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByIdApiTest {

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private UserApiMapper userApiMapper;

    @InjectMocks
    private GetUserByIdApi getUserByIdApi;

    @Test
    void returnsUserOutputDtoWhenIdExists() throws UserNotFoundException {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .build();

        UserOutputDto outputDto = UserOutputDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(Set.of(Roles.USER))
                .build();

        when(getUserByIdUseCase.execute(id)).thenReturn(user);
        when(userApiMapper.toDto(user)).thenReturn(outputDto);

        UserOutputDto result = getUserByIdApi.execute(id);

        assertEquals(outputDto, result);
        verify(getUserByIdUseCase, times(1)).execute(id);
        verify(userApiMapper, times(1)).toDto(user);
    }

    @Test
    void throwsUserNotFoundExceptionWhenIdDoesNotExist() throws UserNotFoundException {
        UUID id = UUID.randomUUID();

        when(getUserByIdUseCase.execute(id)).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> getUserByIdApi.execute(id));
        verify(getUserByIdUseCase, times(1)).execute(id);
        verify(userApiMapper, never()).toDto(any());
    }
}
