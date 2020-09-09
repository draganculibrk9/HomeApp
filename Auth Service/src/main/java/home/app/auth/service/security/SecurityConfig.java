package home.app.auth.service.security;

import home.app.auth.service.services.UserDetailsServiceImpl;
import home.app.grpc.AuthServiceGrpc;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.AccessPredicateVoter;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BasicGrpcAuthenticationReader();
    }


    @Bean
    public GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
        final ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();

        source.set(AuthServiceGrpc.METHOD_LOGIN, AccessPredicate.permitAll());
        source.set(AuthServiceGrpc.METHOD_REGISTER, AccessPredicate.permitAll());
        source.set(AuthServiceGrpc.METHOD_GET_USERS, AccessPredicate.hasRole("SYSTEM_ADMINISTRATOR"));
        source.set(AuthServiceGrpc.METHOD_TOGGLE_BLOCK_ON_USER, AccessPredicate.hasRole("SYSTEM_ADMINISTRATOR"));
        source.set(AuthServiceGrpc.METHOD_VALIDATE_TOKEN, AccessPredicate.authenticated());

        source.setDefault(AccessPredicate.denyAll());

        return source;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        final List<AccessDecisionVoter<?>> voters = new ArrayList<>();
        voters.add(new AccessPredicateVoter());

        return new UnanimousBased(voters);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().logout().and().authorizeRequests().antMatchers("**").permitAll();
        httpSecurity.headers().frameOptions().disable();
    }
}
