package ar.com.pichidev.homestock.user.entrypoint.api;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.usecase.CreateUserUseCase;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.mapper.UserApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserApi {
    private final CreateUserUseCase createUserUseCase;
    private final UserApiMapper userApiMapper;

    public UserOutputDto execute(CreateUserInputDto dto) {
        User user = this.createUserUseCase.execute(
                this.userApiMapper.toDomain(dto)
        );

        return this.userApiMapper.toDto(user);
    }
}
