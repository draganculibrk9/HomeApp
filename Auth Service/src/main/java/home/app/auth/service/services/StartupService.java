package home.app.auth.service.services;

import home.app.auth.service.model.User;
import home.app.auth.service.model.UserRole;
import home.app.auth.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StartupService {
    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createSystemAdmin() {
        if (userRepository.findByRole(UserRole.SYSTEM_ADMINISTRATOR) == null) {
            User sysAdmin = new User();
            sysAdmin.setEmail("admin@home.app");
            sysAdmin.setFirstName("");
            sysAdmin.setLastName("");
            sysAdmin.setAddress(null);
            sysAdmin.setPassword("@dm1n");
            sysAdmin.setPhone("");
            sysAdmin.setRole(UserRole.SYSTEM_ADMINISTRATOR);
            sysAdmin.setId(null);
            sysAdmin.setBlocked(false);

            userRepository.save(sysAdmin);
        }
    }
}
