package ar.com.pichidev.template.user.entrypoint.api.mapper;

import ar.com.pichidev.template.user.core.entity.User;
import ar.com.pichidev.template.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.template.user.entrypoint.api.dto.output.UserOutputDto;
import org.springframework.stereotype.Component;

@Component
public class UserApiMapper {
    public UserOutputDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserOutputDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public User toDomain(CreateUserInputDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        return User.createAndValidate(dto.getId(),dto.getName(), dto.getLastName(), dto.getEmail());
    }
}
