package ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapters;

import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthorizationCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationCodeJpaAdapter  extends JpaRepository<AuthorizationCodeModel, String> {
}
