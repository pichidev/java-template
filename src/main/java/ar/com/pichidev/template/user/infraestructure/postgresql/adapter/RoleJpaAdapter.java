package ar.com.pichidev.template.user.infraestructure.postgresql.adapter;

import ar.com.pichidev.template.user.infraestructure.postgresql.orm.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaAdapter extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findByName(String name);
}
