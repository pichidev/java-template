package ar.com.pichidev.homestock.user.infraestructure.postgresql.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@RequiredArgsConstructor
public class RoleModel {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

}
