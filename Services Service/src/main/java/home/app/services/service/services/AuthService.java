package home.app.services.service.services;

import home.app.grpc.AuthServiceGrpc;
import home.app.grpc.RegistrationRequest;
import home.app.grpc.RegistrationResponse;
import home.app.grpc.UserMessage;
import home.app.grpc.api.mappers.UserMapper;
import home.app.grpc.api.model.User;
import home.app.grpc.api.repositories.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
        UserMessage user = request.getRegistration();

        try {
            User newUser = userRepository.save(userMapper.toEntity(user));

            RegistrationResponse response = RegistrationResponse.newBuilder().setUserId(newUser.getId()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
