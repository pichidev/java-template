package ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm;

import ar.com.pichidev.homestock.auth.infrastructure.postgresql.mapper.MapToJsonConverter;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

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

    @Column(name = "user_payload")
    @Type(JsonType.class)
    Map<String, Object> userPayload;

    @Column(name = "expires_at")
    Instant expiresAt;
}
