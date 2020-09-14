package home.app.household.service.services;

import home.app.grpc.*;
import home.app.household.service.mappers.HouseholdMapper;
import home.app.household.service.mappers.TransactionMapper;
import home.app.household.service.mappers.TransactionTypeMapper;
import home.app.household.service.model.Household;
import home.app.household.service.model.Transaction;
import home.app.household.service.model.TransactionType;
import home.app.household.service.repositories.HouseholdRepository;
import home.app.household.service.repositories.TransactionRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class HouseholdServiceUnitTests {
    @Autowired
    private HouseholdService householdService;

    @Autowired
    private TransactionTypeMapper transactionTypeMapper;

    @MockBean
    private HouseholdRepository householdRepository;

    @MockBean
    private HouseholdMapper householdMapper;

    @MockBean
    private TransactionMapper transactionMapper;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void getHouseholdById_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        HouseholdByIdRequest request = HouseholdByIdRequest.newBuilder()
                .setId(id)
                .build();

        when(householdRepository.getById(id)).thenReturn(null);

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
        long id = 0L;

        HouseholdByIdRequest request = HouseholdByIdRequest.newBuilder()
                .setId(id)
                .build();

        Household household = Household.builder()
                .id(id)
                .transactions(new HashSet<>())
                .owner("owner@owner.com")
                .balance(0.0)
                .build();

        HouseholdMessage householdMessage = HouseholdMessage.newBuilder()
                .setBalance(household.getBalance())
                .setId(id)
                .setOwner(household.getOwner())
                .build();

        when(householdRepository.getById(id)).thenReturn(household);
        when(householdMapper.toDTO(household)).thenReturn(householdMessage);

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
        assertEquals(householdMessage, message);
    }

    @Test
    public void getHousehold_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        String owner = "owner@owner.com";

        HouseholdRequest request = HouseholdRequest.newBuilder()
                .setOwner(owner)
                .build();

        when(householdRepository.getByOwner(owner)).thenReturn(null);

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
        String owner = "owner@owner.com";

        HouseholdRequest request = HouseholdRequest.newBuilder()
                .setOwner(owner)
                .build();

        Household household = Household.builder()
                .id(0L)
                .transactions(new HashSet<>())
                .owner(owner)
                .balance(0.0)
                .build();

        HouseholdMessage householdMessage = HouseholdMessage.newBuilder()
                .setBalance(household.getBalance())
                .setId(household.getId())
                .setOwner(owner)
                .build();

        when(householdRepository.getByOwner(owner)).thenReturn(household);
        when(householdMapper.toDTO(household)).thenReturn(householdMessage);

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
        assertEquals(householdMessage, message);
    }

    @Test
    public void getTransactions_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        TransactionsRequest request = TransactionsRequest.newBuilder()
                .setHouseholdId(id)
                .build();

        when(householdRepository.getById(id)).thenReturn(null);

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
        long id = 0L;

        TransactionsRequest request = TransactionsRequest.newBuilder()
                .setHouseholdId(id)
                .build();

        Transaction t1 = Transaction.builder()
                .date(new Date())
                .amount(100.0)
                .id(0L)
                .name("inc")
                .type(TransactionType.INCOME)
                .build();

        Transaction t2 = Transaction.builder()
                .date(new Date())
                .amount(600.0)
                .id(1L)
                .name("exp")
                .type(TransactionType.EXPENDITURE)
                .build();

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(t1);
        transactions.add(t2);

        Household household = Household.builder()
                .balance(-500.0)
                .owner("owner@owner.com")
                .id(0L)
                .transactions(transactions)
                .build();

        when(householdRepository.getById(id)).thenReturn(household);

        TransactionMessage transactionMessage1 = TransactionMessage.newBuilder()
                .setTransactionType(transactionTypeMapper.toDTO(t1.getType()))
                .setAmount(t1.getAmount())
                .setDate(t1.getDate().getTime())
                .setId(t1.getId())
                .setName(t1.getName())
                .build();

        TransactionMessage transactionMessage2 = TransactionMessage.newBuilder()
                .setTransactionType(transactionTypeMapper.toDTO(t2.getType()))
                .setAmount(t2.getAmount())
                .setDate(t2.getDate().getTime())
                .setId(t2.getId())
                .setName(t2.getName())
                .build();

        TransactionResponse tr1 = TransactionResponse.newBuilder()
                .setTransaction(transactionMessage1)
                .build();

        TransactionResponse tr2 = TransactionResponse.newBuilder()
                .setTransaction(transactionMessage2)
                .build();

        when(transactionMapper.toDTO(t1)).thenReturn(transactionMessage1);
        when(transactionMapper.toDTO(t2)).thenReturn(transactionMessage2);

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
        assertTrue(responseObserver.getValues().contains(tr1));
        assertTrue(responseObserver.getValues().contains(tr2));
    }

    @Test
    public void getTransaction_transactionDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        GetOrDeleteTransactionRequest request = GetOrDeleteTransactionRequest.newBuilder()
                .setTransactionId(id)
                .build();

        when(transactionRepository.findById(id)).thenReturn(null);

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
        long id = 0L;

        GetOrDeleteTransactionRequest request = GetOrDeleteTransactionRequest.newBuilder()
                .setTransactionId(id)
                .build();

        Transaction transaction = Transaction.builder()
                .date(new Date())
                .amount(100.0)
                .id(id)
                .name("inc")
                .type(TransactionType.INCOME)
                .build();

        when(transactionRepository.getById(id)).thenReturn(transaction);

        TransactionMessage transactionMessage = TransactionMessage.newBuilder()
                .setTransactionType(transactionTypeMapper.toDTO(transaction.getType()))
                .setAmount(transaction.getAmount())
                .setDate(transaction.getDate().getTime())
                .setId(id)
                .setName(transaction.getName())
                .build();

        when(transactionMapper.toDTO(transaction)).thenReturn(transactionMessage);

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
        assertEquals(transactionMessage, message);
    }
}
