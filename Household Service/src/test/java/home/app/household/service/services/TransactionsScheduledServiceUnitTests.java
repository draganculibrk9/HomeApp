package home.app.household.service.services;

import home.app.household.service.model.Household;
import home.app.household.service.model.Transaction;
import home.app.household.service.model.TransactionType;
import home.app.household.service.repositories.HouseholdRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest({TransactionsScheduledService.class, HouseholdRepository.class})
@ActiveProfiles("test")
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*", "com.sun.org.apache.xalan.*"})
public class TransactionsScheduledServiceUnitTests {
    @Test
    public void isSameDay_sameDaysPassed_returnedTrue() throws Exception {
        TransactionsScheduledService transactionsScheduledService = new TransactionsScheduledService();
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime());

        assertTrue(WhiteboxImpl.invokeMethod(transactionsScheduledService, "isSameDay", date1, date2));
    }

    @Test
    public void isSameDay_differentDaysPassed_returnedFalse() throws Exception {
        TransactionsScheduledService transactionsScheduledService = new TransactionsScheduledService();
        Date date1 = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        Date date2 = calendar.getTime();

        assertFalse(WhiteboxImpl.invokeMethod(transactionsScheduledService, "isSameDay", date1, date2));
    }

    @Test
    public void calculateBalanceForHousehold_householdHasNoTransactionsToday_balanceNotChanged() throws Exception {
        Double startingBalance = 0.0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 6);

        Date transactionDate = calendar.getTime();

        Transaction income = Transaction.builder()
                .amount(300.0)
                .date(transactionDate)
                .id(0L)
                .name("Income1")
                .type(TransactionType.INCOME)
                .build();

        Transaction expenditure = Transaction.builder()
                .amount(600.0)
                .date(transactionDate)
                .id(1L)
                .name("Expenditure1")
                .type(TransactionType.EXPENDITURE)
                .build();

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(income);
        transactions.add(expenditure);

        Household household = Household.builder()
                .balance(startingBalance)
                .id(0L)
                .owner("owner@owner.com")
                .transactions(transactions)
                .build();

        TransactionsScheduledService transactionsScheduledService = new TransactionsScheduledService();

        Double afterMethodBalance = WhiteboxImpl.invokeMethod(transactionsScheduledService, "calculateBalanceForHousehold", household);

        assertEquals(startingBalance, afterMethodBalance);
    }

    @Test
    public void calculateBalanceForHousehold_householdHasTransactionsToday_balanceCalculatedCorrectly() throws Exception {
        Double startingBalance = 0.0;

        Transaction income1 = Transaction.builder()
                .amount(300.0)
                .date(new Date())
                .id(0L)
                .name("Income1")
                .type(TransactionType.INCOME)
                .build();

        Transaction income2 = Transaction.builder()
                .amount(100.0)
                .date(new Date())
                .id(1L)
                .name("Income2")
                .type(TransactionType.INCOME)
                .build();

        Transaction expenditure = Transaction.builder()
                .amount(600.0)
                .date(new Date())
                .id(2L)
                .name("Expenditure1")
                .type(TransactionType.EXPENDITURE)
                .build();

        Set<Transaction> transactions = new HashSet<>();
        transactions.add(income1);
        transactions.add(income2);
        transactions.add(expenditure);

        Household household = Household.builder()
                .balance(startingBalance)
                .id(0L)
                .owner("owner@owner.com")
                .transactions(transactions)
                .build();

        TransactionsScheduledService transactionsScheduledService = new TransactionsScheduledService();

        Double expectedBalance = -200.0;

        Double afterMethodBalance = WhiteboxImpl.invokeMethod(transactionsScheduledService, "calculateBalanceForHousehold", household);

        assertEquals(expectedBalance, afterMethodBalance);
    }
}
