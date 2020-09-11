package home.app.grpc.api.services;

import home.app.grpc.api.model.User;
import home.app.grpc.api.model.UserRole;
import home.app.grpc.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartupService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void createSystemAdmin() {
        if (userRepository.findByRole(UserRole.SYSTEM_ADMINISTRATOR) == null) {
            User sysAdmin = new User();
            sysAdmin.setEmail("admin@home.app");
            sysAdmin.setFirstName("");
            sysAdmin.setLastName("");
            sysAdmin.setAddress(null);
            sysAdmin.setPassword(passwordEncoder.encode("@dm1n"));
            sysAdmin.setPhone("");
            sysAdmin.setRole(UserRole.SYSTEM_ADMINISTRATOR);
            sysAdmin.setId(null);
            sysAdmin.setBlocked(false);

            userRepository.save(sysAdmin);
        }
    }
}
