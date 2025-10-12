package ar.com.pichidev.homestock.user.infraestructure.postgresql.orm;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;

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
