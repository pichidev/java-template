package ar.com.pichidev.homestock.user.infraestructure.postgresql.initializer;

import ar.com.pichidev.homestock.user.core.entity.Roles;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.adapter.RoleJpaAdapter;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.RoleModel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RolesInitializer implements ApplicationRunner {

    private final RoleJpaAdapter roleJpaAdapter;

    public RolesInitializer(RoleJpaAdapter roleJpaAdapter) {
        this.roleJpaAdapter = roleJpaAdapter;
    }

    //MEJOR REVISAR ESTO
    @Override
    public void run(ApplicationArguments args) throws Exception  {
        for (Roles roleEnum : Roles.values()) {
            // Si no existe en la DB, lo creamos
            roleJpaAdapter.findByName(roleEnum.name())
                    .orElseGet(() -> {
                        RoleModel role = new RoleModel(UUID.randomUUID(), roleEnum.name());
                        return roleJpaAdapter.save(role);
                    });
        }
    }

}