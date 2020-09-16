package home.app.household.service.services;

import home.app.grpc.*;
import home.app.household.service.mappers.TransactionTypeMapper;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class HouseholdServiceIntegrationTests {
    @Autowired
    private HouseholdService householdService;

    
    @Test
    public void getHouseholdById_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        HouseholdByIdRequest request = HouseholdByIdRequest.newBuilder()
                .setId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Household with id '%d' not found", id)));

        StreamRecorder<HouseholdResponse> responseObserver = StreamRecorder.create();

        householdService.getHouseholdById(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void getHouseholdById_householdExists_HouseholdResponseReturned() throws Exception {
        long id = 13L;

        HouseholdByIdRequest request = HouseholdByIdRequest.newBuilder()
                .setId(id)
                .build();

        StreamRecorder<HouseholdResponse> responseObserver = StreamRecorder.create();

        householdService.getHouseholdById(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        HouseholdResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);

        HouseholdMessage message = response.getHousehold();
        assertNotNull(message);
    }

    @Test
    public void getHousehold_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        String owner = "owner@owner.com";

        HouseholdRequest request = HouseholdRequest.newBuilder()
                .setOwner(owner)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Household with owner '%s' not found", owner)));

        StreamRecorder<HouseholdResponse> responseObserver = StreamRecorder.create();

        householdService.getHousehold(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void getHousehold_householdExists_HouseholdResponseReturned() throws Exception {
        String owner = "user1@user.com";

        HouseholdRequest request = HouseholdRequest.newBuilder()
                .setOwner(owner)
                .build();

        StreamRecorder<HouseholdResponse> responseObserver = StreamRecorder.create();

        householdService.getHousehold(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        HouseholdResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);

        HouseholdMessage message = response.getHousehold();
        assertNotNull(message);
    }

    @Test
    public void getTransactions_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        TransactionsRequest request = TransactionsRequest.newBuilder()
                .setHouseholdId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Household with id '%d' not found", id)));

        StreamRecorder<TransactionResponse> responseObserver = StreamRecorder.create();

        householdService.getTransactions(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void getTransactions_householdExists_TransactionResponsesReturned() throws Exception {
        long id = 13L;

        TransactionsRequest request = TransactionsRequest.newBuilder()
                .setHouseholdId(id)
                .build();

        StreamRecorder<TransactionResponse> responseObserver = StreamRecorder.create();

        householdService.getTransactions(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(2, responseObserver.getValues().size());
        assertNotNull(responseObserver.getValues().get(0).getTransaction());
        assertNotNull(responseObserver.getValues().get(1).getTransaction());
    }

    @Test
    public void getTransaction_transactionDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        GetOrDeleteTransactionRequest request = GetOrDeleteTransactionRequest.newBuilder()
                .setTransactionId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Transaction with id '%d' not found", id)));

        StreamRecorder<TransactionResponse> responseObserver = StreamRecorder.create();

        householdService.getTransaction(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void getTransaction_transactionExists_TransactionResponseReturned() throws Exception {
        long id = 16L;

        GetOrDeleteTransactionRequest request = GetOrDeleteTransactionRequest.newBuilder()
                .setTransactionId(id)
                .build();

        StreamRecorder<TransactionResponse> responseObserver = StreamRecorder.create();

        householdService.getTransaction(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        TransactionResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);

        TransactionMessage message = response.getTransaction();
        assertNotNull(message);
    }

    @Test
    @Transactional
    @Rollback
    public void editTransaction_transactionDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        TransactionMessage transactionMessage = TransactionMessage.newBuilder()
                .setName("Income")
                .setId(id)
                .setDate(new Date().getTime())
                .setAmount(100.0)
                .setTransactionType(TransactionMessage.TransactionType.INCOME)
                .build();

        CreateOrEditTransactionRequest request = CreateOrEditTransactionRequest.newBuilder()
                .setTransaction(transactionMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Transaction with id '%d' not found", id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.editTransaction(request, responseObserver);

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
    public void editTransaction_transactionDoesExist_SuccessResponseReturned() throws Exception {
        long id = 16L;

        TransactionMessage transactionMessage = TransactionMessage.newBuilder()
                .setName("Income")
                .setId(id)
                .setDate(new Date().getTime())
                .setAmount(100.0)
                .setTransactionType(TransactionMessage.TransactionType.INCOME)
                .build();

        CreateOrEditTransactionRequest request = CreateOrEditTransactionRequest.newBuilder()
                .setTransaction(transactionMessage)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.editTransaction(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void createTransaction_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        long household_id = 0L;

        TransactionMessage transactionMessage = TransactionMessage.newBuilder()
                .setName("Income")
                .setDate(new Date().getTime())
                .setAmount(100.0)
                .setTransactionType(TransactionMessage.TransactionType.INCOME)
                .build();

        CreateOrEditTransactionRequest request = CreateOrEditTransactionRequest.newBuilder()
                .setHouseholdId(household_id)
                .setTransaction(transactionMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Household with id '%d' not found", household_id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.createTransaction(request, responseObserver);

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
    public void createTransaction_householdExists_SuccessResponseReturned() throws Exception {
        long household_id = 13L;

        TransactionMessage transactionMessage = TransactionMessage.newBuilder()
                .setName("Income")
                .setDate(new Date().getTime())
                .setAmount(100.0)
                .setTransactionType(TransactionMessage.TransactionType.INCOME)
                .build();

        CreateOrEditTransactionRequest request = CreateOrEditTransactionRequest.newBuilder()
                .setHouseholdId(household_id)
                .setTransaction(transactionMessage)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.createTransaction(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void deleteTransaction_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        GetOrDeleteTransactionRequest request = GetOrDeleteTransactionRequest.newBuilder()
                .setTransactionId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Transaction with id '%d' not found", id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.deleteTransaction(request, responseObserver);

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
    public void deleteTransaction_transactionFound_SuccessResponseReturned() throws Exception {
        long id = 16L;

        GetOrDeleteTransactionRequest request = GetOrDeleteTransactionRequest.newBuilder()
                .setTransactionId(id)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.deleteTransaction(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void createHousehold_ownerAlreadyHasHousehold_AlreadyExistStatusReturned() throws Exception {
        String owner = "user1@user.com";

        HouseholdRequest request = HouseholdRequest.newBuilder()
                .setOwner(owner)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(String.format("Household for owner '%s' already exists", owner)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.createHousehold(request, responseObserver);

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
    public void createHousehold_ownerDoesNotHaveHousehold_SuccessResponseReturned() throws Exception {
        String owner = "owner@owner.com";

        HouseholdRequest request = HouseholdRequest.newBuilder()
                .setOwner(owner)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        householdService.createHousehold(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }
}
