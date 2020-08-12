package home.app.auth.service.services;

import home.app.auth.service.mappers.UserMapper;
import home.app.auth.service.model.User;
import home.app.auth.service.repositories.UserRepository;
import home.app.auth.service.security.TokenService;
import home.app.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
        RegistrationMessage registrationMessage = request.getRegistration();

        if (userRepository.findByEmail(registrationMessage.getEmail()) != null) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("User with specified email already exists")
                            .asRuntimeException()
            );
            return;
        }

        try {
            User newUser = userRepository.save(userMapper.toEntity(registrationMessage));
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


    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        LoginMessage loginMessage = request.getLogin();
        ;
        Authentication authentication;
        try {
            authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginMessage.getEmail(),
                            loginMessage.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenService.generateToken((UserDetails) authentication.getDetails());

            LoginResponse response = LoginResponse.newBuilder().setToken(token).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
