package home.app.auth.service.configurations;

import home.app.grpc.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.channelfactory.InProcessChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import net.devh.boot.grpc.server.serverfactory.GrpcServerFactory;
import net.devh.boot.grpc.server.serverfactory.InProcessGrpcServerFactory;
import net.devh.boot.grpc.server.service.GrpcServiceDefinition;
import net.devh.boot.grpc.server.service.GrpcServiceDiscoverer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@EnableAutoConfiguration
public class AuthServiceTestConfiguration {
    @Bean
    public HouseholdServiceGrpc.HouseholdServiceImplBase householdServiceImplBase() {
        return new HouseholdServiceGrpc.HouseholdServiceImplBase() {
            @Override
            public void createHousehold(HouseholdRequest request, StreamObserver<SuccessResponse> responseObserver) {
            }
        };
    }

    @Bean
    public AuthServiceGrpc.AuthServiceImplBase authServiceImplBase() {
        return new AuthServiceGrpc.AuthServiceImplBase() {
            @Override
            public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
            }
        };
    }

    @Bean
    GrpcChannelFactory grpcChannelFactory(final GrpcChannelsProperties properties,
                                          final GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
        return new InProcessChannelFactory(properties, globalClientInterceptorRegistry) {

            @Override
            protected InProcessChannelBuilder newChannelBuilder(final String name) {
                return super.newChannelBuilder("test") // Use fixed inMemory channel name: test
                        .usePlaintext();
            }

        };
    }

    @Bean
    GrpcServerFactory grpcServerFactory(final GrpcServerProperties properties,
                                        final GrpcServiceDiscoverer discoverer) {
        final InProcessGrpcServerFactory factory = new InProcessGrpcServerFactory("test", properties);
        for (final GrpcServiceDefinition service : discoverer.findGrpcServices()) {
            factory.addService(service);
        }
        return factory;
    }
}
