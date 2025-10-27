package ar.com.pichidev.template.user.infraestructure.postgresql.repository;

import ar.com.pichidev.template.common.exception.RepositoryException;
import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.template.user.infraestructure.postgresql.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class GetUserByIdDbRepositoryIntegrationTest {

    @Autowired
    private GetUserByIdDbRepository repository;

    @Autowired
    private CreateUserDbRepository createUserDbRepository;

    @Autowired
    private UserJpaAdapter userJpaAdapter;

    @Test
    void shouldReturnUserWhenExists() {
        UUID id = UUID.randomUUID();

        User user = User.createAndValidate(
                id,
                "Agustin",
                "Carrizo",
                "agustin@example.com",
                Set.of(Roles.USER)
        );

        createUserDbRepository.execute(user);

        Optional<User> found = repository.execute(id);

        assertTrue(found.isPresent());
        assertEquals("Agustin", found.get().getName());
        assertEquals("Carrizo", found.get().getLastName());
        assertEquals("agustin@example.com", found.get().getEmail());
        assertEquals(1, found.get().getRoles().size());
    }

    @Test
    void shouldReturnEmptyWhenUserDoesNotExist() {
        UUID randomId = UUID.randomUUID();

        Optional<User> found = repository.execute(randomId);

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldThrowRepositoryExceptionOnDatabaseError() {
        UserJpaAdapter mockAdapter = mock(UserJpaAdapter.class);
        UserMapper userMapper = new UserMapper();

        when(mockAdapter.findById(any(UUID.class))).thenThrow(new RuntimeException("Simulated DB failure"));

        GetUserByIdDbRepository repo = new GetUserByIdDbRepository(mockAdapter, userMapper);

        assertThrows(RepositoryException.class, () -> repo.execute(UUID.randomUUID()));
    }
}
