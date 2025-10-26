package ar.com.pichidev.homestock.auth.infrastructure.integration.external.mapper;

import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
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
