package ar.com.pichidev.homestock.user.infraestructure.postgresql.mapper;

import ar.com.pichidev.homestock.common.core.entity.Roles;
import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.RoleModel;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.UserModel;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    private static final UUID uuid = UUID.randomUUID();

    @Test
    void toDomainShouldReturnNullIfModelIsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toDomainShouldMapBasicFields() {
        UserModel model = UserModel.builder()
                .id(uuid)
                .name("John")
                .lastName("Doe")
                .email("john@example.com")
                .roles(Set.of())
                .build();

        User result = mapper.toDomain(model);

        assertEquals(uuid, result.getId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void toDomainShouldMapRoles() {
        RoleModel admin = RoleModel.builder().name("ADMIN").build();
        RoleModel user = RoleModel.builder().name("USER").build();

        UserModel model = UserModel.builder()
                .id(uuid)
                .name("Test")
                .lastName("Test-l")
                .email("a@test.es")
                .roles(Set.of(admin, user))
                .build();

        User result = mapper.toDomain(model);

        assertEquals(Set.of(Roles.ADMIN, Roles.USER), result.getRoles());
    }

    @Test
    void toDomainShouldHandleNullRoles() {
        UserModel model = UserModel.builder()
                .id(uuid)
                .name("Test")
                .lastName("Test-l")
                .email("a@test.es")
                .roles(null)
                .build();

        User result = mapper.toDomain(model);

        assertNotNull(result.getRoles());
        assertTrue(result.getRoles().isEmpty());
    }

    @Test
    void toModelShouldReturnNullIfDomainIsNull() {
        assertNull(mapper.toModel(null));
    }

    @Test
    void toModelShouldMapBasicFields() {
        User domain = User.builder()
                .id(uuid)
                .name("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .roles(Set.of())
                .build();

        UserModel result = mapper.toModel(domain);

        assertEquals(uuid, result.getId());
        assertEquals("Jane", result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void toModelShouldMapRoles() {
        User domain = User.builder()
                .id(uuid)
                .name("Test")
                .lastName("Test-l")
                .email("a@test.es")
                .roles(Set.of(Roles.ADMIN, Roles.USER))
                .build();

        UserModel result = mapper.toModel(domain);

        assertEquals(2, result.getRoles().size());
        assertTrue(result.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN")));
        assertTrue(result.getRoles().stream().anyMatch(r -> r.getName().equals("USER")));
    }

    @Test
    void toModelShouldHandleNullRoles() {
        User domain = User.builder()
                .id(uuid)
                .name("Test")
                .lastName("Test-l")
                .email("a@test.es")
                .roles(null)
                .build();

        UserModel result = mapper.toModel(domain);

        assertNotNull(result.getRoles());
        assertTrue(result.getRoles().isEmpty());
    }
}
