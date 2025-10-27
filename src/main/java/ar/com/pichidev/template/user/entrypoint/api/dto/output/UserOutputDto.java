package ar.com.pichidev.template.user.entrypoint.api.dto.output;

import java.util.Set;
import java.util.UUID;

import ar.com.pichidev.template.common.core.entity.Roles;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class UserOutputDto {
    private final UUID id;
    private final String name;
    private final String lastName;
    private final String email;
    private final Set<Roles> roles;
}
