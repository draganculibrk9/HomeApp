package home.app.household.service.services;

import home.app.grpc.AddressMessage;
import home.app.grpc.RegistrationRequest;
import home.app.grpc.RegistrationResponse;
import home.app.grpc.UserMessage;
import home.app.grpc.api.mappers.UserMapper;
import home.app.grpc.api.mappers.UserRoleMapper;
import home.app.grpc.api.model.Address;
import home.app.grpc.api.model.User;
import home.app.grpc.api.repositories.UserRepository;
import home.app.household.service.configs.AuthServiceTestConfig;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringJUnitConfig(AuthServiceTestConfig.class)
public class AuthServiceUnitTests {
    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepositoryMocked;

    @MockBean
    private UserMapper userMapperMocked;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void register_RegistrationResponseReturned() throws Exception {
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
        User afterSave = beforeSave.toBuilder().id(id).build();

        when(userMapperMocked.toEntity(userMessage)).thenReturn(beforeSave);
        when(userRepositoryMocked.save(beforeSave)).thenReturn(afterSave);

        StreamRecorder<RegistrationResponse> responseObserver = StreamRecorder.create();

        authService.register(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        RegistrationResponse response = responseObserver.getValues().get(0);

        assertNotNull(response);
        assertEquals(id, response.getUserId());
    }
}
