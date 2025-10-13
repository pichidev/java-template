package ar.com.pichidev.homestock.user.entrypoint.api.dto.input;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreateUserInputDto {
    private final UUID id;
    private final String name;
    private final String lastName;
    private final String email;
}
