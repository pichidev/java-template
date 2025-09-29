package ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm;

import ar.com.pichidev.homestock.auth.core.entity.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "auth_identities",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "provider_user_id"})
},
indexes = {
        @Index(columnList = "user_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AuthIdentityModel {
    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OAuthProvider provider;
    @Column(name = "provider_user_id", nullable = false)
    private String providerUserId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }


}
