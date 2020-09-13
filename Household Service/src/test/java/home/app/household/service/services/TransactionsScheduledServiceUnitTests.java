package home.app.household.service.services;

import home.app.household.service.repositories.HouseholdRepository;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class TransactionsScheduledServiceUnitTests {
    @MockBean
    private HouseholdRepository householdRepository;
}
