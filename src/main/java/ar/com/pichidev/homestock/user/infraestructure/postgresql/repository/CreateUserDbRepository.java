package ar.com.pichidev.homestock.user.infraestructure.postgresql.repository;

import ar.com.pichidev.homestock.user.core.entity.User;
import ar.com.pichidev.homestock.user.core.interfaces.repository.CreateUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CreateUserDbRepository implements CreateUserRepository {


    @Override
    public void execute(User user) {
    }
}
