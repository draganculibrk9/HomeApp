package home.app.auth.service.services;

import home.app.grpc.api.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class UserDetailsServiceImpIntegrationTests {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadByUsername_userNotFound_UsernameNotFoundExceptionThrown() {
        String email = "email@email.com";

        String message = String.format("No user found with email '%s'", email);

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email)
        );
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void loadByUsername_userFound_userReturned() {
        String email = "user1@user.com";

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }
}
