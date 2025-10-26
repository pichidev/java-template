package ar.com.pichidev.homestock.user.entrypoint.api.mapper;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.input.CreateUserInputDto;
import ar.com.pichidev.homestock.user.entrypoint.api.dto.output.UserOutputDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
                .roles(user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public User toDomain(CreateUserInputDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        return User.createAndValidate(dto.getId(),dto.getName(), dto.getLastName(), dto.getEmail());
    }
}
