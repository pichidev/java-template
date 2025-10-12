package ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm;

import ar.com.pichidev.homestock.auth.infrastructure.postgresql.mapper.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "authorization_codes")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Builder
public class AuthorizationCodeModel {
    @Id
    String value;

    @Column(name = "user_payload",columnDefinition = "jsonb")
    @Convert(converter = MapToJsonConverter.class)
    Map<String, Object> userPayload;

    @Column(name = "expires_at")
    Instant expiresAt;
}
