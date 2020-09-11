package home.app.household.service.security;

import home.app.grpc.AuthServiceGrpc;
import home.app.grpc.HouseholdServiceGrpc;
import home.app.grpc.api.security.BaseSecurityConfiguration;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(BaseSecurityConfiguration.class)
@Configuration
public class SecurityConfiguration {
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
        source.set(AuthServiceGrpc.METHOD_REGISTER, AccessPredicate.permitAll());

        source.setDefault(AccessPredicate.denyAll());

        return source;
    }
}
