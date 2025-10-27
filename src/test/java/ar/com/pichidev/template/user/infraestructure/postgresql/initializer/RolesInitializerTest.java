package ar.com.pichidev.template.user.infraestructure.postgresql.initializer;

import ar.com.pichidev.template.common.core.entity.Roles;
import ar.com.pichidev.template.user.infraestructure.postgresql.adapter.RoleJpaAdapter;
import ar.com.pichidev.template.user.infraestructure.postgresql.orm.RoleModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesInitializerTest {

    @Mock
    private RoleJpaAdapter roleJpaAdapter;

    @Mock
    private ApplicationArguments applicationArguments;

    @InjectMocks
    private RolesInitializer rolesInitializer;


    @Test
    void shouldNotSaveAnyRoleIfAllExist() {
        for (Roles role : Roles.values()) {
            when(roleJpaAdapter.findByName(role.name())).thenReturn(Optional.of(new RoleModel(role.name())));
        }

        rolesInitializer.run(applicationArguments);

        verify(roleJpaAdapter, never()).save(any(RoleModel.class));
    }

    @Test
    void shouldSaveRoleIfItDoesNotExist() {
        Roles missingRole = Roles.values()[0]; // tomamos uno arbitrario
        when(roleJpaAdapter.findByName(missingRole.name())).thenReturn(Optional.empty());
        for (int i = 1; i < Roles.values().length; i++) {
            Roles existing = Roles.values()[i];
            when(roleJpaAdapter.findByName(existing.name())).thenReturn(Optional.of(new RoleModel(existing.name())));
        }

        rolesInitializer.run(applicationArguments);

        verify(roleJpaAdapter, times(1)).save(argThat(roleModel ->
                roleModel.getName().equals(missingRole.name())
        ));
    }
}
