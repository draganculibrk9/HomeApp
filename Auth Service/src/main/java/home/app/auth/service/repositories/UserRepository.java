package home.app.auth.service.repositories;

import home.app.auth.service.model.User;
import home.app.auth.service.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByRole(UserRole role);
}
