package home.app.auth.service.repositories;

import home.app.auth.service.model.User;
import home.app.auth.service.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByRole(UserRole role);

    List<User> findAllByRoleNot(UserRole role);
}
