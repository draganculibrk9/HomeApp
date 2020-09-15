package home.app.auth.service.services;

import home.app.auth.service.configurations.AuthServiceTestConfiguration;
import home.app.grpc.*;
import home.app.grpc.api.mappers.UserMapper;
import home.app.grpc.api.mappers.UserRoleMapper;
import home.app.grpc.api.model.Address;
import home.app.grpc.api.model.User;
import home.app.grpc.api.repositories.UserRepository;
import home.app.grpc.api.security.TokenService;
import home.app.grpc.api.services.UserDetailsServiceImpl;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.testing.StreamRecorder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {UserDetailsServiceImpl.class, AuthService.class,
        BCryptPasswordEncoder.class, UserRoleMapper.class})
@DirtiesContext
@SpringJUnitConfig(AuthServiceTestConfiguration.class)
public class AuthServiceUnitTests {
    @GrpcClient("test")
    private HouseholdServiceGrpc.HouseholdServiceBlockingStub householdServiceStubMocked;

    @GrpcClient("test")
    private AuthServiceGrpc.AuthServiceBlockingStub householdServiceAuthMocked;

    @GrpcClient("test")
    private AuthServiceGrpc.AuthServiceBlockingStub servicesServiceAuthMocked;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @MockBean
    private UserRepository userRepositoryMocked;

    @MockBean
    private UserMapper userMapperMocked;

    @MockBean
    private TokenService tokenServiceMocked;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceMocked;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void register_emailExists_AlreadyExistsStatus() throws Exception {
        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setStreet("street")
                .setNumber(1)
                .setCountry("country")
                .setCity("city")
                .build();

        String email = "user@user.com";
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

        when(userRepositoryMocked.findByEmail(email)).thenReturn(new User());

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

    @Test // :TODO mock
    public void register_ok_RegistrationResponseReturned() throws Exception {
        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setStreet("street")
                .setNumber(1)
                .setCountry("country")
                .setCity("city")
                .build();

        String email = "user@user.com";
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

        Address address = new Address();
        address.setCity(addressMessage.getCity());
        address.setCountry(addressMessage.getCountry());
        address.setId(0L);
        address.setNumber(addressMessage.getNumber());
        address.setStreet(addressMessage.getStreet());

        User beforeSave = new User();
        beforeSave.setAddress(address);
        beforeSave.setBlocked(userMessage.getBlocked());
        beforeSave.setEmail(userMessage.getEmail());
        beforeSave.setFirstName(userMessage.getFirstName());
        beforeSave.setLastName(userMessage.getLastName());
        beforeSave.setPassword(passwordEncoder.encode(userMessage.getPassword()));
        beforeSave.setPhone(userMessage.getPhone());
        beforeSave.setRole(userRoleMapper.toEntity(userMessage.getRole()));
        beforeSave.setId(null);

        long id = 0L;
        User afterSave = beforeSave.toBuilder().build();
        afterSave.setId(id);

        when(userRepositoryMocked.findByEmail(email)).thenReturn(null);
        when(userMapperMocked.toEntity(userMessage)).thenReturn(beforeSave);
        when(userRepositoryMocked.save(beforeSave)).thenReturn(afterSave);
        when(householdServiceStubMocked.createHousehold(HouseholdRequest.newBuilder().setOwner(afterSave.getEmail()).build()))
                .thenReturn(SuccessResponse.newBuilder().setSuccess(true).build());
        when(householdServiceAuthMocked.register(request)).thenReturn(RegistrationResponse.newBuilder().setUserId(id).build());
        when(servicesServiceAuthMocked.register(request)).thenReturn(RegistrationResponse.newBuilder().setUserId(id).build());

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
        assertEquals(id, response.getUserId());
    }

    @Test
    public void login_badCredentials_InvalidArgumentStatus() throws Exception {
        String email = "user@user.com";
        String password = "password";

        LoginMessage loginMessage = LoginMessage.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();

        LoginRequest request = LoginRequest.newBuilder()
                .setLogin(loginMessage)
                .build();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(authenticationToken)).thenThrow(new AuthenticationException("Bad username or password") {
        });

        StatusRuntimeException expected = new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Bad username or password"));

        StreamRecorder<LoginResponse> responseObserver = StreamRecorder.create();

        authService.login(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals("INVALID_ARGUMENT: Bad username or password", responseObserver.getError().getMessage());
    }

    @Test
    public void login_okCredentials_LoginResponseReturned() throws Exception {
        String email = "user@user.com";
        String password = "password";

        LoginMessage loginMessage = LoginMessage.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();

        LoginRequest request = LoginRequest.newBuilder()
                .setLogin(loginMessage)
                .build();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        when(userDetailsServiceMocked.loadUserByUsername(email)).thenReturn(user);

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        when(tokenServiceMocked.generateToken(user)).thenReturn(token);

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
        assertEquals(token, response.getToken());
    }

    @Test
    public void toggleBlockOnUser_userNotFound_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ToggleBlockRequest request = ToggleBlockRequest.newBuilder()
                .setUserId(id)
                .build();

        when(userRepositoryMocked.findById(id)).thenReturn(Optional.empty());

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
    public void toggleBlockOnUser_userFound_SuccessResponseReturned() throws Exception {
        long id = 0L;

        ToggleBlockRequest request = ToggleBlockRequest.newBuilder()
                .setUserId(id)
                .build();

        User user = User.builder()
                .id(id)
                .blocked(false)
                .build();

        when(userRepositoryMocked.findById(id)).thenReturn(Optional.of(user));

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
