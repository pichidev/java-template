package ar.com.pichidev.homestock.auth.infrastructure.integration.external;

import ar.com.pichidev.homestock.auth.core.entity.AuthUser;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.infrastructure.integration.external.mapper.UserTokenInformationMapper;
import ar.com.pichidev.homestock.user.entrypoint.api.CreateUserApi;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserExternalServiceTest {

    @Mock
    private CreateUserApi createUserApi;

    @Mock
    private UserTokenInformationMapper userTokenInformationMapper;

    @InjectMocks
    private CreateUserExternalService service;

    @Test
    void executeCreatesUserAndMapsToTokenInformation() {
        UUID userId = UUID.randomUUID();
        AuthUser authUser = new AuthUser(
                userId,
                "test@example.com",
                "John",
                "Doe"
        );

        UserOutputDto userOutput = UserOutputDto.builder()
                .id(userId)
                .name("John")
                .lastName("Doe")
                .email("test@example.com")
                .roles(Set.of("USER"))
                .build();

        UserTokenInformation expectedTokenInfo = UserTokenInformation.builder()
                .id(userId)
                .name("John")
                .lastname("Doe")
                .email("test@example.com")
                .roles(new String[]{"USER"})
                .build();

        when(createUserApi.execute(any(CreateUserInputDto.class))).thenReturn(userOutput);
        when(userTokenInformationMapper.fromOutput(userOutput)).thenReturn(expectedTokenInfo);

        UserTokenInformation result = service.execute(authUser);

        assertNotNull(result);
        assertEquals(expectedTokenInfo, result);

        ArgumentCaptor<CreateUserInputDto> inputCaptor = ArgumentCaptor.forClass(CreateUserInputDto.class);
        verify(createUserApi).execute(inputCaptor.capture());

        CreateUserInputDto capturedInput = inputCaptor.getValue();
        assertEquals(userId, capturedInput.getId());
        assertEquals("John", capturedInput.getName());
        assertEquals("Doe", capturedInput.getLastName());
        assertEquals("test@example.com", capturedInput.getEmail());

        verify(userTokenInformationMapper).fromOutput(userOutput);
    }

    @Test
    void executeMapsAuthUserFieldsCorrectly() {
        UUID userId = UUID.randomUUID();
        AuthUser authUser = new AuthUser(
                userId,
                "jane.smith@example.com",
                "Jane",
                "Smith"
        );

        UserOutputDto userOutput = UserOutputDto.builder()
                .id(userId)
                .name("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .roles(Set.of("USER", "ADMIN"))
                .build();

        UserTokenInformation tokenInfo = UserTokenInformation.builder()
                .id(userId)
                .name("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .roles(new String[]{"USER", "ADMIN"})
                .build();

        when(createUserApi.execute(any(CreateUserInputDto.class))).thenReturn(userOutput);
        when(userTokenInformationMapper.fromOutput(userOutput)).thenReturn(tokenInfo);

        service.execute(authUser);

        ArgumentCaptor<CreateUserInputDto> inputCaptor = ArgumentCaptor.forClass(CreateUserInputDto.class);
        verify(createUserApi).execute(inputCaptor.capture());

        CreateUserInputDto capturedInput = inputCaptor.getValue();
        assertEquals(authUser.id(), capturedInput.getId());
        assertEquals(authUser.name(), capturedInput.getName());
        assertEquals(authUser.lastname(), capturedInput.getLastName());
        assertEquals(authUser.email(), capturedInput.getEmail());
    }
}