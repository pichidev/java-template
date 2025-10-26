package ar.com.pichidev.homestock.user.infraestructure.postgresql.mapper;

import ar.com.pichidev.homestock.user.core.entity.Roles;
import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.RoleModel;
import ar.com.pichidev.homestock.user.infraestructure.postgresql.orm.UserModel;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toDomain(UserModel model) {
        if (model == null) {
            return null;
        }

        Set<Roles> domainRoles = model.getRoles() == null
                ? Set.of()
                : model.getRoles().stream()
                .map(roleModel -> Roles.valueOf(roleModel.getName().toUpperCase()))
                .collect(Collectors.toSet());

        return User.fromPersistence(
                model.getId(),
                model.getName(),
                model.getLastName(),
                model.getEmail(),
                domainRoles);
    }

    public UserModel toModel(User domain) {
        if (domain == null) {
            return null;
        }

        Set<RoleModel> roleModels = domain.getRoles() == null
                ? Set.of()
                : domain.getRoles().stream()
                .map(role -> RoleModel.builder()
                        .name(role.name())
                        .build())
                .collect(Collectors.toSet());

        return UserModel.builder()
                .id(domain.getId())
                .name(domain.getName())
                .lastName(domain.getLastName())
                .email(domain.getEmail())
                .roles(roleModels)
                .build();
    }
}
