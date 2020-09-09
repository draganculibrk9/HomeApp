package home.app.services.service.security;

import home.app.grpc.HouseholdServiceGrpc;
import home.app.grpc.ServicesServiceGrpc;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.AccessPredicateVoter;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BasicGrpcAuthenticationReader();
    }


    @Bean
    public GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
        final ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();

        source.set(ServicesServiceGrpc.METHOD_SEARCH_SERVICES, AccessPredicate.hasRole("USER"));
        source.set(ServicesServiceGrpc.METHOD_GET_SERVICE, AccessPredicate.hasAnyRole("USER", "SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_GET_SERVICES_BY_ADMINISTRATOR, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_CREATE_SERVICE, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_EDIT_SERVICE, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_DELETE_SERVICE, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_GET_ACCOMMODATIONS, AccessPredicate.hasAnyRole("USER", "SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_EDIT_ACCOMMODATION, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_CREATE_ACCOMMODATION, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_DELETE_ACCOMMODATION, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_GET_ACCOMMODATION_REQUESTS_FOR_ADMINISTRATOR, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_GET_ACCOMMODATION_REQUESTS, AccessPredicate.hasRole("USER"));
        source.set(ServicesServiceGrpc.METHOD_DECIDE_ACCOMMODATION_REQUEST, AccessPredicate.hasRole("SERVICE_ADMINISTRATOR"));
        source.set(ServicesServiceGrpc.METHOD_REQUEST_ACCOMMODATION, AccessPredicate.hasRole("USER"));

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
