package home.app.services.service.repositories;

import home.app.services.service.model.AccommodationRequest;
import home.app.services.service.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class AccommodationRequestRepositoryIntegrationTests {
    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Test
    public void getAllByAdministrator_administratorDoesNotExist_emptyListReturned() {
        String admin = "admin@admin.com";

        List<AccommodationRequest> accommodationRequests = accommodationRequestRepository.getAllByAdministrator(admin, Status.PENDING);

        assertEquals(0, accommodationRequests.size());
    }

    @Test
    public void getAllByAdministrator_administratorDoesExist_AccommodationRequestsReturned() {
        String admin = "serviceadmin1@user.com";

        List<AccommodationRequest> accommodationRequests = accommodationRequestRepository.getAllByAdministrator(admin, Status.PENDING);

        assertEquals(4, accommodationRequests.size());
    }
}
