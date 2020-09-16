package home.app.household.service.repositories;

import home.app.household.service.model.Household;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class HouseholdRepositoryIntegrationTests {
    @Autowired
    private HouseholdRepository householdRepository;

    @Test
    public void getByTransactionId_transactionWithSuchIdDoesNotExist_nullReturned() {
        long transaction_id = 0L;

        Household household = householdRepository.getByTransactionId(transaction_id);
        assertNull(household);
    }

    // :TODO: cover other case
}
