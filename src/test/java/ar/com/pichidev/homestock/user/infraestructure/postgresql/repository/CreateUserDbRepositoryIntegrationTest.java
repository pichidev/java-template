package ar.com.pichidev.homestock.user.infraestructure.postgresql.repository;

import ar.com.pichidev.homestock.common.core.entity.Roles;
import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.adapter.UserJpaAdapter;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.UserModel;
import ar.com.pichidev.homestock.common.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUserDbRepositoryIntegrationTest {

    @Autowired
    private CreateUserDbRepository repository;

    @Autowired
    private UserJpaAdapter userJpaAdapter;

    @Test
    @Transactional
    void shouldPersistUserInDatabase() {
        UUID id = UUID.randomUUID();

        User domain = User.createAndValidate(
                        id,
                "Agustin",
                "Carrizo",
                "agustin@example.com",
                Set.of(Roles.USER)
                );

        repository.execute(domain);

        Optional<UserModel> persisted = userJpaAdapter.findById(id);
        assertTrue(persisted.isPresent());
        assertEquals("Agustin", persisted.get().getName());
        assertEquals("Carrizo", persisted.get().getLastName());
        assertEquals("agustin@example.com", persisted.get().getEmail());
        assertEquals(1, persisted.get().getRoles().size());
    }

    @Test
    void shouldThrowRepositoryExceptionWhenEmailIsDuplicated() {
        User first = User.createAndValidate(
                UUID.randomUUID(),
                "Agustin",
                "Carrizo",
                "duplicate@example.com",
                Set.of(Roles.USER)
        );

        User duplicate = User.createAndValidate(
                UUID.randomUUID(),
                "Agustin",
                "Carrizo",
                "duplicate@example.com",
                Set.of(Roles.USER)
        );

        repository.execute(first);

        assertThrows(RepositoryException.class, () -> repository.execute(duplicate));
        userJpaAdapter.deleteAll();
    }
}
