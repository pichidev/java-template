package ar.com.pichidev.homestock.auth.infrastructure.integration.external.mapper;

import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTokenInformationMapperTest {

    private UserTokenInformationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserTokenInformationMapper();
    }

    @Test
    void fromOutputMapsAllFieldsCorrectly() {
        UUID userId = UUID.randomUUID();
        UserOutputDto output = UserOutputDto.builder()
                .id(userId)
                .name("John")
                .lastName("Doe")
                .email("john@example.com")
                .roles(Set.of("USER", "ADMIN"))
                .build();

        UserTokenInformation result = mapper.fromOutput(output);

        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals("John", result.name());
        assertEquals("Doe", result.lastname());
        assertEquals("john@example.com", result.email());
        assertNotNull(result.roles());
        assertEquals(2, result.roles().length);
        assertTrue(Set.of(result.roles()).contains("USER"));
        assertTrue(Set.of(result.roles()).contains("ADMIN"));
    }

    @Test
    void fromOutputConvertsRolesSetToArray() {
        UUID userId = UUID.randomUUID();
        UserOutputDto output = UserOutputDto.builder()
                .id(userId)
                .name("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .roles(Set.of("USER"))
                .build();

        UserTokenInformation result = mapper.fromOutput(output);

        assertNotNull(result.roles());
        assertInstanceOf(String[].class, result.roles());
        assertEquals(1, result.roles().length);
        assertEquals("USER", result.roles()[0]);
    }

    @Test
    void fromOutputHandlesEmptyRoles() {
        UUID userId = UUID.randomUUID();
        UserOutputDto output = UserOutputDto.builder()
                .id(userId)
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .roles(Set.of())
                .build();

        UserTokenInformation result = mapper.fromOutput(output);

        assertNotNull(result.roles());
        assertEquals(0, result.roles().length);
    }
}