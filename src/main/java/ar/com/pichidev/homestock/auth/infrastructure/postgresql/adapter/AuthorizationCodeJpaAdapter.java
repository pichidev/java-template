package ar.com.pichidev.homestock.auth.infrastructure.postgresql.adapter;

import ar.com.pichidev.homestock.auth.infrastructure.postgresql.orm.AuthorizationCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationCodeJpaAdapter  extends JpaRepository<AuthorizationCodeModel, String> {
}
