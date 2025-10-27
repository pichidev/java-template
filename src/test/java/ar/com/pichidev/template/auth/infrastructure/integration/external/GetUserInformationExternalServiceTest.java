package ar.com.pichidev.template.auth.infrastructure.integration.external;

import ar.com.pichidev.template.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.template.auth.infrastructure.integration.external.mapper.UserTokenInformationMapper;
import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.core.exception.UserNotFoundException;
import ar.com.pichidev.template.user.entrypoint.api.GetUserByEmailApi;
import ar.com.pichidev.template.user.entrypoint.api.GetUserByIdApi;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
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
class GetUserInformationExternalServiceTest {

    @Mock
    private UserTokenInformationMapper userTokenInformationMapper;

    @Mock
    private GetUserByEmailApi getUserByEmailApi;

    @Mock
    private GetUserByIdApi getUserByIdApi;

    @InjectMocks
    private GetUserInformationExternalService service;

    @Test
    void byIdExecuteReturnsUserTokenInformationWhenUserExists() {
        UUID userId = UUID.randomUUID();
        UserOutputDto userOutput = UserOutputDto.builder()
                .id(userId)
                .name("John")
                .lastName("Doe")
                .email("john@example.com")
                .roles(Set.of(Roles.USER))
                .build();

        UserTokenInformation expectedTokenInfo = UserTokenInformation.builder()
                .id(userId)
                .name("John")
                .lastname("Doe")
                .email("john@example.com")
                .roles(new String[]{"USER"})
                .build();

        when(getUserByIdApi.execute(userId)).thenReturn(userOutput);
        when(userTokenInformationMapper.fromOutput(userOutput)).thenReturn(expectedTokenInfo);

        UserTokenInformation result = service.byIdExecute(userId);

        assertNotNull(result);
        assertEquals(expectedTokenInfo, result);
        verify(getUserByIdApi).execute(userId);
        verify(userTokenInformationMapper).fromOutput(userOutput);
    }

    @Test
    void byIdExecuteReturnsNullWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(getUserByIdApi.execute(userId)).thenThrow(new UserNotFoundException());

        UserTokenInformation result = service.byIdExecute(userId);

        assertNull(result);
        verify(getUserByIdApi).execute(userId);
        verify(userTokenInformationMapper, never()).fromOutput(any());
    }

    @Test
    void byEmailExecuteReturnsUserTokenInformationWhenUserExists() {
        String email = "jane@example.com";
        UUID userId = UUID.randomUUID();
        UserOutputDto userOutput = UserOutputDto.builder()
                .id(userId)
                .name("Jane")
                .lastName("Smith")
                .email(email)
                .roles(Set.of(Roles.USER, Roles.ADMIN))
                .build();

        UserTokenInformation expectedTokenInfo = UserTokenInformation.builder()
                .id(userId)
                .name("Jane")
                .lastname("Smith")
                .email(email)
                .roles(new String[]{"USER", "ADMIN"})
                .build();

        when(getUserByEmailApi.execute(email)).thenReturn(userOutput);
        when(userTokenInformationMapper.fromOutput(userOutput)).thenReturn(expectedTokenInfo);

        UserTokenInformation result = service.byEmailExecute(email);

        assertNotNull(result);
        assertEquals(expectedTokenInfo, result);
        verify(getUserByEmailApi).execute(email);
        verify(userTokenInformationMapper).fromOutput(userOutput);
    }

    @Test
    void byEmailExecuteReturnsNullWhenUserNotFound() {
        String email = "notfound@example.com";

        when(getUserByEmailApi.execute(email)).thenThrow(new UserNotFoundException());

        UserTokenInformation result = service.byEmailExecute(email);

        assertNull(result);
        verify(getUserByEmailApi).execute(email);
        verify(userTokenInformationMapper, never()).fromOutput(any());
    }
}