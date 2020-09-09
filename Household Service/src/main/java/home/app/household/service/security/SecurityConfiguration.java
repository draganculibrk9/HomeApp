package home.app.household.service.security;

import home.app.grpc.HouseholdServiceGrpc;
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

        source.set(HouseholdServiceGrpc.METHOD_GET_HOUSEHOLD, AccessPredicate.hasAnyRole("USER", "SERVICE_ADMINISTRATOR"));
        source.set(HouseholdServiceGrpc.METHOD_GET_HOUSEHOLD_BY_ID, AccessPredicate.hasAnyRole("USER", "SERVICE_ADMINISTRATOR"));
        source.set(HouseholdServiceGrpc.METHOD_CREATE_HOUSEHOLD, AccessPredicate.permitAll());
        source.set(HouseholdServiceGrpc.METHOD_GET_TRANSACTIONS, AccessPredicate.hasRole("USER"));
        source.set(HouseholdServiceGrpc.METHOD_GET_TRANSACTION, AccessPredicate.hasRole("USER"));
        source.set(HouseholdServiceGrpc.METHOD_EDIT_TRANSACTION, AccessPredicate.hasRole("USER"));
        source.set(HouseholdServiceGrpc.METHOD_CREATE_TRANSACTION, AccessPredicate.hasRole("USER"));
        source.set(HouseholdServiceGrpc.METHOD_DELETE_TRANSACTION, AccessPredicate.hasRole("USER"));

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
