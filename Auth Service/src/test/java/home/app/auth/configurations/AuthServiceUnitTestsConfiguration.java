package home.app.auth.configurations;

import home.app.grpc.api.model.Address;
import home.app.grpc.api.model.User;
import home.app.grpc.api.model.UserRole;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class AuthServiceUnitTestsConfiguration {
    @Bean
    @Primary
    public UserDetailsService mockUserDetailsService() {
        User user = User.builder()
                .email("user@user.com")
                .password("password")
                .blocked(false)
                .id(0L)
                .role(UserRole.USER)
                .address(Address.builder().build())
                .firstName("u")
                .lastName("u")
                .phone("+387 649467443")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
