package home.app.household.service.configs;

import home.app.grpc.api.mappers.UserRoleMapper;
import home.app.household.service.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthServiceTestConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthService authService() {
        return new AuthService();
    }

    @Bean
    public UserRoleMapper userRoleMapper() {
        return new UserRoleMapper();
    }
}
