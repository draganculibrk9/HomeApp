package home.app.grpc.api.services;

import home.app.grpc.api.model.User;
import home.app.grpc.api.model.UserRole;
import home.app.grpc.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserDetailsServiceImplUnitTests {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepositoryMocked;

    @Test
    public void loadByUsername_userNotFound_UsernameNotFoundExceptionThrown() {
        String email = "email@email.com";

        when(userRepositoryMocked.findByEmail(email)).thenReturn(null);

        String message = String.format("No user found with email '%s'", email);

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email)
        );
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void loadByUsername_userFound_userReturned() {
        String email = "email@email.com";
        String password = "pwrd";
        UserRole role = UserRole.USER;

        User user = User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();

        when(userRepositoryMocked.findByEmail(email)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertNotNull(userDetails.getAuthorities());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(role));
    }
}
