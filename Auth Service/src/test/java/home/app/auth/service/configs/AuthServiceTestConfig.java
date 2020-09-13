package home.app.auth.service.configs;

import home.app.auth.service.services.AuthService;
import home.app.grpc.*;
import home.app.grpc.api.mappers.UserRoleMapper;
import home.app.grpc.api.services.UserDetailsServiceImpl;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.AdditionalAnswers.delegatesTo;

@Configuration
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class, // Create required server beans
        GrpcServerFactoryAutoConfiguration.class, // Select server implementation
        GrpcClientAutoConfiguration.class // Support @GrpcClient annotation
})
public class AuthServiceTestConfig {
    @Bean
    public HouseholdServiceGrpc.HouseholdServiceBlockingStub householdServiceBlockingStub() {
        return Mockito.mock(HouseholdServiceGrpc.HouseholdServiceBlockingStub.class,
                delegatesTo(
                        new HouseholdServiceGrpc.HouseholdServiceImplBase() {
                            @Override
                            public void createHousehold(HouseholdRequest request, StreamObserver<SuccessResponse> responseObserver) {
                            }
                        }
                ));
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub() {
        return Mockito.mock(AuthServiceGrpc.AuthServiceBlockingStub.class,
                delegatesTo(
                        new AuthServiceGrpc.AuthServiceImplBase() {
                            @Override
                            public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
                            }
                        }
                ));
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthService authService() {
        return new AuthService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRoleMapper userRoleMapper() {
        return new UserRoleMapper();
    }
}
