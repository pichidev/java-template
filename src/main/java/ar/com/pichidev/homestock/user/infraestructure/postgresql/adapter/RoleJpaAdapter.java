package ar.com.pichidev.homestock.user.infraestructure.postgresql.adapter;

import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaAdapter extends JpaRepository<RoleModel, UUID> {
    Optional<RoleModel> findByName(String name);
}
