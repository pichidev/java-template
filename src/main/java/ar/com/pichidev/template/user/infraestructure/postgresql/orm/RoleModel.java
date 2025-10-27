package ar.com.pichidev.template.user.infraestructure.postgresql.orm;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    @Id
    @Column(nullable = false, unique = true)
    private String name;

}
