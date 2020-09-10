package home.app.auth.service.security;

import home.app.grpc.AuthServiceGrpc;
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

        source.set(AuthServiceGrpc.METHOD_LOGIN, AccessPredicate.permitAll());
        source.set(AuthServiceGrpc.METHOD_REGISTER, AccessPredicate.permitAll());
        source.set(AuthServiceGrpc.METHOD_GET_USERS, AccessPredicate.hasRole("SYSTEM_ADMINISTRATOR"));
        source.set(AuthServiceGrpc.METHOD_TOGGLE_BLOCK_ON_USER, AccessPredicate.hasRole("SYSTEM_ADMINISTRATOR"));
        source.set(AuthServiceGrpc.METHOD_VALIDATE_TOKEN, AccessPredicate.authenticated());

        source.setDefault(AccessPredicate.denyAll());

        return source;
    }
}
