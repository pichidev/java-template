package ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapter;

import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthorizationCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface AuthorizationCodeJpaAdapter  extends JpaRepository<AuthorizationCodeModel, String> {
    @Modifying
    @Query(value = "INSERT INTO authorization_codes(value, user_payload, expires_at) " +
            "VALUES (:value, CAST(:userPayload AS jsonb), :expiresAt)", nativeQuery = true)
    void insertAuthorizationCode(@Param("value") String value,
                                 @Param("userPayload") String userPayload,
                                 @Param("expiresAt") Instant expiresAt);

}
