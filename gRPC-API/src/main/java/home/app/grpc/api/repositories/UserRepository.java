package home.app.grpc.api.repositories;

import home.app.grpc.api.model.User;
import home.app.grpc.api.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByRole(UserRole role);

    List<User> findAllByRoleNot(UserRole role);
}
