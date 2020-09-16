package home.app.grpc.api.services;

import home.app.grpc.api.model.UserRole;
import home.app.grpc.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StartupServiceIntegrationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createSystemAdmin_systemAdminDoesNotExist_systemAdminIsCreated() {
        assertNotNull(userRepository.findByRole(UserRole.SYSTEM_ADMINISTRATOR));
    }
}
