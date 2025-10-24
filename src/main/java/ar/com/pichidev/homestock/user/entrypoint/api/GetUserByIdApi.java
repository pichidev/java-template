package ar.com.pichidev.homestock.user.entrypoint.api;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.exception.UserNotFoundException;
import ar.com.pichidev.homestock.user.core.usecase.GetUserByIdUseCase;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.mapper.UserApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserByIdApi {
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UserApiMapper userApiMapper;

    public UserOutputDto execute(UUID id) throws UserNotFoundException {
        User user = this.getUserByIdUseCase.execute(
                id
        );

        return this.userApiMapper.toDto(user);
    }
}
