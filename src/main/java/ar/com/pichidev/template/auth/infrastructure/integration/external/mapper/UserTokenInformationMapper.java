package ar.com.pichidev.template.auth.infrastructure.integration.external.mapper;

import ar.com.pichidev.template.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
import org.springframework.stereotype.Component;

@Component
public class UserTokenInformationMapper {

    public UserTokenInformation fromOutput(UserOutputDto output) {
        return UserTokenInformation.builder()
                .id(output.getId())
                .name(output.getName())
                .lastname(output.getLastName())
                .email(output.getEmail())
                .roles(output.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}
