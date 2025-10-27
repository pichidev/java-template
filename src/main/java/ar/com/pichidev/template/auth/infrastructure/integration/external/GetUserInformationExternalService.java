package ar.com.pichidev.template.auth.infrastructure.integration.external;

import ar.com.pichidev.template.auth.core.entity.UserTokenInformation;
import ar.com.pichidev.template.auth.core.interfaces.integration.GetUserInformationPort;
import ar.com.pichidev.template.auth.infrastructure.integration.external.mapper.UserTokenInformationMapper;
import ar.com.pichidev.template.user.core.exception.UserNotFoundException;
import ar.com.pichidev.template.user.entrypoint.api.GetUserByEmailApi;
import ar.com.pichidev.template.user.entrypoint.api.GetUserByIdApi;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserInformationExternalService implements GetUserInformationPort {
    private final UserTokenInformationMapper userTokenInformationMapper;
    private final GetUserByEmailApi getUserByEmailApi;
    private final GetUserByIdApi getUserByIdApi;

    @Override
    public UserTokenInformation byIdExecute(UUID userId) {
        try{
            UserOutputDto output = this.getUserByIdApi.execute(userId);
            return this.userTokenInformationMapper.fromOutput(output);
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    @Override
    public UserTokenInformation byEmailExecute(String email) {
        try{
            UserOutputDto output = this.getUserByEmailApi.execute(email);
            return this.userTokenInformationMapper.fromOutput(output);
        } catch (UserNotFoundException e) {
            return null;
        }
    }
}
