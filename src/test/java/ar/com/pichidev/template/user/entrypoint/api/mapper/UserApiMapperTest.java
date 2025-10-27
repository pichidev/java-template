package ar.com.pichidev.template.user.entrypoint.api.mapper;

import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserApiMapperTest {

    private final UserApiMapper userApiMapper = new UserApiMapper();

    @Test
    void returnsUserOutputDtoWhenUserIsValid() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .roles(Set.of(Roles.USER))
                .build();

        UserOutputDto result = userApiMapper.toDto(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(Set.of(Roles.USER), result.getRoles());
    }

    @Test
    void returnsNullWhenUserIsNull() {
        UserOutputDto result = userApiMapper.toDto(null);

        assertNull(result);
    }

    @Test
    void returnsUserWhenInputDtoIsValid() {
        CreateUserInputDto dto = CreateUserInputDto.builder()
                .id(UUID.randomUUID())
                .name("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .build();

        User result = userApiMapper.toDomain(dto);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getEmail(), result.getEmail());
    }

    @Test
    void throwsIllegalArgumentExceptionWhenInputDtoIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userApiMapper.toDomain(null));
    }
}
