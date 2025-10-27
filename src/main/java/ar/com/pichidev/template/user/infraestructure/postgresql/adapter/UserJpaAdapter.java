package ar.com.pichidev.template.user.infraestructure.postgresql.adapter;

import ar.com.pichidev.template.user.infraestructure.postgresql.orm.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaAdapter extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
}
