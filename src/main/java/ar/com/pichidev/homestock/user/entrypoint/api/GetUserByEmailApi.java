package ar.com.pichidev.homestock.user.entrypoint.api;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.UserNotFoundException;
import ar.com.pichidev.homestock.user.core.usecase.GetUserByEmailUseCase;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.mapper.UserApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserByEmailApi {
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final UserApiMapper userApiMapper;

    public UserOutputDto execute(String email) throws UserNotFoundException {
        User user = this.getUserByEmailUseCase.execute(
                email
        );

        return this.userApiMapper.toDto(user);
    }
}
