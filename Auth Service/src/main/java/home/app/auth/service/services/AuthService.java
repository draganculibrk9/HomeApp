package home.app.auth.service.services;

import home.app.auth.service.mappers.UserMapper;
import home.app.auth.service.model.User;
import home.app.auth.service.model.UserRole;
import home.app.auth.service.repositories.UserRepository;
import home.app.auth.service.security.TokenService;
import home.app.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@GrpcService
@Transactional
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @GrpcClient("householdService")
    private HouseholdServiceGrpc.HouseholdServiceBlockingStub householdServiceStub;

    @Override
    public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
        UserMessage user = request.getRegistration();

        if (userRepository.findByEmail(user.getEmail()) != null) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(String.format("User with email '%s' already exists", user.getEmail()))
                            .asRuntimeException()
            );
            return;
        }

        try {
            User newUser = userRepository.save(userMapper.toEntity(user));

            if (!householdServiceStub.createHousehold(HouseholdRequest.newBuilder().setOwner(newUser.getEmail()).build()).getSuccess()) {
                responseObserver.onError(
                        Status.INTERNAL
                                .withDescription("Failed to create household for new user")
                                .asRuntimeException()
                );
                return;
            }

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
        Authentication authentication;
        try {
            authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginMessage.getEmail(),
                            loginMessage.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenService.generateToken((User) authentication.getPrincipal());

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

    @Override
    public void getUsers(GetUsersRequest request, StreamObserver<UserResponse> responseObserver) {
        userRepository.findAllByRoleNot(UserRole.SYSTEM_ADMINISTRATOR).forEach(u -> {
            UserResponse response = UserResponse.newBuilder()
                    .setUser(userMapper.toDTO(u))
                    .build();

            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }

    @Override
    public void toggleBlockOnUser(ToggleBlockRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Optional<User> u = userRepository.findById(request.getUserId());

        if (!u.isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("User with id '%d' not found", request.getUserId()))
                            .asRuntimeException()
            );
            return;
        }

        User user = u.get();
        user.setBlocked(!user.getBlocked());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }

        SuccessResponse response = SuccessResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
