package home.app.auth.service.services;

import home.app.grpc.*;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class AuthServiceIntegrationTests {
    @Autowired
    private AuthService authService;

    @Rule
    private final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Autowired
    private GrpcChannelsProperties grpcChannelsProperties;

    @Test
    public void register_emailExists_AlreadyExistsStatus() throws Exception {
        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setStreet("street")
                .setNumber(1)
                .setCountry("country")
                .setCity("city")
                .build();

        String email = "user1@user.com";
        UserMessage userMessage = UserMessage.newBuilder()
                .setRole(UserMessage.Role.USER)
                .setPhone("+381 640085454")
                .setPassword("password")
                .setLastName("User")
                .setFirstName("User")
                .setEmail(email)
                .setBlocked(false)
                .setAddress(addressMessage)
                .build();

        RegistrationRequest request = RegistrationRequest.newBuilder()
                .setRegistration(userMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(String.format("User with email '%s' already exists", email)));

        StreamRecorder<RegistrationResponse> responseObserver = StreamRecorder.create();

        authService.register(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void register_everythingOk_RegistrationResponseReturned() throws Exception {
        HouseholdServiceGrpc.HouseholdServiceImplBase householdServiceImplBase = mock(HouseholdServiceGrpc.HouseholdServiceImplBase.class,
                AdditionalAnswers.delegatesTo(new HouseholdServiceGrpc.HouseholdServiceImplBase() {
                    @Override
                    public void createHousehold(HouseholdRequest request, StreamObserver<SuccessResponse> responseObserver) {
                        responseObserver.onNext(SuccessResponse.newBuilder().setSuccess(true).build());
                        responseObserver.onCompleted();
                    }
                }));

        AuthServiceGrpc.AuthServiceImplBase authServiceImplBase = mock(AuthServiceGrpc.AuthServiceImplBase.class,
                AdditionalAnswers.delegatesTo(new AuthServiceGrpc.AuthServiceImplBase() {
                    @Override
                    public void register(RegistrationRequest request, StreamObserver<RegistrationResponse> responseObserver) {
                        responseObserver.onNext(RegistrationResponse.newBuilder().setUserId(0L).build());
                        responseObserver.onCompleted();
                    }
                }));

        Server server = grpcCleanup.register(
                InProcessServerBuilder
                        .forName(grpcChannelsProperties.getGlobalChannel().getAddress().getSchemeSpecificPart())
                        .directExecutor()
                        .addService(householdServiceImplBase)
                        .addService(authServiceImplBase)
                        .build()
                        .start()
        );

        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setStreet("street")
                .setNumber(1)
                .setCountry("country")
                .setCity("city")
                .build();

        String email = "newuser@user.com";
        UserMessage userMessage = UserMessage.newBuilder()
                .setRole(UserMessage.Role.USER)
                .setPhone("+381 640085454")
                .setPassword("password")
                .setLastName("User")
                .setFirstName("User")
                .setEmail(email)
                .setBlocked(false)
                .setAddress(addressMessage)
                .build();

        RegistrationRequest request = RegistrationRequest.newBuilder()
                .setRegistration(userMessage)
                .build();

        StreamRecorder<RegistrationResponse> responseObserver = StreamRecorder.create();

        authService.register(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());

        List<RegistrationResponse> results = responseObserver.getValues();
        assertEquals(1, results.size());

        RegistrationResponse response = results.get(0);
        assertNotNull(response);

        server.shutdown().awaitTermination();
    }

    @Test
    public void login_badCredentials_InvalidArgumentStatus() throws Exception {
        String email = "user@user.com";
        String password = "jhgjdfjfhgsfd";

        LoginMessage loginMessage = LoginMessage.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();

        LoginRequest request = LoginRequest.newBuilder()
                .setLogin(loginMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Bad credentials"));

        StreamRecorder<LoginResponse> responseObserver = StreamRecorder.create();

        authService.login(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void login_userAccountLocked_InvalidArgumentStatus() throws Exception {
        String email = "user3@user.com";
        String password = "u";

        LoginMessage loginMessage = LoginMessage.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();

        LoginRequest request = LoginRequest.newBuilder()
                .setLogin(loginMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("User account is locked"));

        StreamRecorder<LoginResponse> responseObserver = StreamRecorder.create();

        authService.login(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals("INVALID_ARGUMENT: User account is locked", responseObserver.getError().getMessage());
    }

    @Test
    public void login_okCredentials_LoginResponseReturned() throws Exception {
        String email = "user1@user.com";
        String password = "u";

        LoginMessage loginMessage = LoginMessage.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();

        LoginRequest request = LoginRequest.newBuilder()
                .setLogin(loginMessage)
                .build();

        StreamRecorder<LoginResponse> responseObserver = StreamRecorder.create();

        authService.login(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        LoginResponse response = responseObserver.getValues().get(0);

        assertNotNull(response);
        assertNotNull(response.getToken());
    }

    @Test
    public void toggleBlockOnUser_userNotFound_NotFoundStatusReturned() throws Exception {
        long id = 100L;

        ToggleBlockRequest request = ToggleBlockRequest.newBuilder()
                .setUserId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(
                Status.NOT_FOUND
                        .withDescription(String.format("User with id '%d' not found", id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        authService.toggleBlockOnUser(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void toggleBlockOnUser_userFound_SuccessResponseReturned() throws Exception {
        long id = 7L;

        ToggleBlockRequest request = ToggleBlockRequest.newBuilder()
                .setUserId(id)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        authService.toggleBlockOnUser(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse successResponse = responseObserver.getValues().get(0);
        assertNotNull(successResponse);
        assertTrue(successResponse.getSuccess());
    }
}
