package home.app.household.service.services;

import home.app.grpc.AddressMessage;
import home.app.grpc.RegistrationRequest;
import home.app.grpc.RegistrationResponse;
import home.app.grpc.UserMessage;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class AuthServiceIntegrationTests {
    @Autowired
    private AuthService authService;

    @Test
    @Transactional
    @Rollback
    public void register_RegistrationResponseReturned() throws Exception {
        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setStreet("street")
                .setNumber(1)
                .setCountry("country")
                .setCity("city")
                .build();

        String email = "newUser@user.com";
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
        assertEquals(1, responseObserver.getValues().size());

        RegistrationResponse response = responseObserver.getValues().get(0);

        assertNotNull(response);
    }
}
