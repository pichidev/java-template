package ar.com.pichidev.homestock.user.infraestructure.postgresql.repository;

import ar.com.pichidev.homestock.common.core.entity.Roles;
import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.mapper.UserMapper;
import ar.com.pichidev.homestock.common.exception.RepositoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetUserByEmailDbRepositoryIntegrationTest {

    @Autowired
    private GetUserByEmailDbRepository repository;

    @Autowired
    private CreateUserDbRepository createUserRepository;

    @Autowired
    private UserJpaAdapter userJpaAdapter;

    @AfterEach
    void cleanUp() {
        userJpaAdapter.deleteAll();
    }

    @Test
    @Transactional
    void shouldReturnUserWhenEmailExists() {
        UUID id = UUID.randomUUID();

        User user = User.createAndValidate(
                id,
                "Agustin",
                "Carrizo",
                "agustin@example.com",
                Set.of(Roles.USER)
        );

        createUserRepository.execute(user);

        Optional<User> found = repository.execute("agustin@example.com");
        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
        assertEquals(user.getName(), found.get().getName());
        assertEquals(user.getLastName(), found.get().getLastName());
        assertEquals(user.getEmail(), found.get().getEmail());
        assertEquals(user.getRoles(), found.get().getRoles());
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        Optional<User> found = repository.execute("nonexistent@example.com");
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldThrowRepositoryExceptionOnDatabaseErrorWithMock() {
        UserJpaAdapter mockAdapter = mock(UserJpaAdapter.class);
        UserMapper userMapper = new UserMapper();

        // Forzamos que findByEmail lance un RuntimeException
        when(mockAdapter.findByEmail(anyString())).thenThrow(new RuntimeException("Simulated DB failure"));

        GetUserByEmailDbRepository repo = new GetUserByEmailDbRepository(mockAdapter, userMapper);

        assertThrows(RepositoryException.class, () -> repo.execute("duplicate@example.com"));
    }

}
