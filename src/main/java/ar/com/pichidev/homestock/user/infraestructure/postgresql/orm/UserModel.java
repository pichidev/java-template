package ar.com.pichidev.homestock.user.infraestructure.postgresql.orm;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class UserModel {
    @Id
    private UUID id;

    private String name;
    private String lastName;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", insertable = false, updatable = false)
    )
    @Builder.Default
    private Set<RoleModel> roles = new HashSet<>();
}
