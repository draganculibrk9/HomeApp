package home.app.household.service.repositories;

import home.app.household.service.model.Household;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class HouseholdRepositoryIntegrationTests {
    @Autowired
    private HouseholdRepository householdRepository;

    @Test
    public void getByTransactionId_transactionWithSuchIdDoesNotExist_nullReturned() {
        long transaction_id = 0L;

        Household household = householdRepository.getByTransactionId(transaction_id);
        assertNull(household);
    }

    @Test
    public void getByTransactionId_transactionWithSuchIdExists_transactionReturned() {
        long transaction_id = 16L;

        Household household = householdRepository.getByTransactionId(transaction_id);
        assertNotNull(household);
    }
}
