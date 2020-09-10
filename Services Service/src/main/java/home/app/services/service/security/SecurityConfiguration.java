package home.app.services.service.security;

import home.app.grpc.ServicesServiceGrpc;
import home.app.grpc.api.security.BaseSecurityConfiguration;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Import(BaseSecurityConfiguration.class)
@Configuration
public class SecurityConfiguration {
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
}
