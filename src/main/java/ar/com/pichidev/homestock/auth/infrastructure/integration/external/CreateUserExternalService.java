package ar.com.pichidev.homestock.auth.infrastructure.integration.external;

import ar.com.pichidev.homestock.auth.core.entity.AuthUser;
import ar.com.pichidev.homestock.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.homestock.auth.core.interfaces.integration.CreateUserPort;
import ar.com.pichidev.homestock.auth.infrastructure.integration.external.mapper.UserTokenInformationMapper;
import ar.com.pichidev.homestock.user.entrypoint.api.CreateUserApi;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserExternalService implements CreateUserPort {
    private final CreateUserApi createUserApi;
    private final UserTokenInformationMapper userTokenInformationMapper;

    @Override
    public UserTokenInformation execute(AuthUser authUser) {
        UserOutputDto output  = this.createUserApi.execute(
                CreateUserInputDto.builder()
                        .name(authUser.name())
                        .id(authUser.id())
                        .email(authUser.email())
                        .lastName(authUser.lastname())
                        .build()
        );

        return userTokenInformationMapper.fromOutput(output);
    }
}
