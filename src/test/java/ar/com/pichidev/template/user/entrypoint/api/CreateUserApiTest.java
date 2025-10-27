
package ar.com.pichidev.template.user.entrypoint.api;

import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.core.usecase.CreateUserUseCase;
import ar.com.pichidev.template.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
import ar.com.pichidev.template.user.entrypoint.api.mapper.UserApiMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserApiTest {

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserApiMapper userApiMapper;

    @InjectMocks
    private CreateUserApi createUserApi;

    @Test
    void returnsUserOutputDtoWhenInputIsValid() {
        CreateUserInputDto dto = CreateUserInputDto.builder()
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .build();

        User user = User.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();

        UserOutputDto outputDto = UserOutputDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(Set.of(Roles.USER))
                .build();

        when(userApiMapper.toDomain(dto)).thenReturn(user);
        when(createUserUseCase.execute(user)).thenReturn(user);
        when(userApiMapper.toDto(user)).thenReturn(outputDto);

        UserOutputDto result = createUserApi.execute(dto);

        assertEquals(outputDto, result);
        verify(userApiMapper, times(1)).toDomain(dto);
        verify(createUserUseCase, times(1)).execute(user);
        verify(userApiMapper, times(1)).toDto(user);
    }

    @Test
    void propagatesExceptionWhenCreateUserUseCaseThrows() {
        CreateUserInputDto dto = CreateUserInputDto.builder()
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .build();

        User user = User.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();

        when(userApiMapper.toDomain(dto)).thenReturn(user);
        doThrow(new RuntimeException("DB error")).when(createUserUseCase).execute(user);

        assertThrows(RuntimeException.class, () -> createUserApi.execute(dto));
        verify(userApiMapper, times(1)).toDomain(dto);
        verify(createUserUseCase, times(1)).execute(user);
        verify(userApiMapper, never()).toDto(any());
    }

    @Test
    void propagatesNullPointerWhenMapperToDomainThrowsForNullInput() {
        when(userApiMapper.toDomain(null)).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> createUserApi.execute(null));
        verify(userApiMapper, times(1)).toDomain(null);
        verify(createUserUseCase, never()).execute(any());
        verify(userApiMapper, never()).toDto(any());
    }

    @Test
    void propagatesExceptionWhenMapperToDtoThrows() {
        CreateUserInputDto dto = CreateUserInputDto.builder()
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .build();

        User user = User.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();

        when(userApiMapper.toDomain(dto)).thenReturn(user);
        when(createUserUseCase.execute(user)).thenReturn(user);
        when(userApiMapper.toDto(user)).thenThrow(new RuntimeException("Mapping error"));

        assertThrows(RuntimeException.class, () -> createUserApi.execute(dto));
        verify(userApiMapper, times(1)).toDomain(dto);
        verify(createUserUseCase, times(1)).execute(user);
        verify(userApiMapper, times(1)).toDto(user);
    }
}
