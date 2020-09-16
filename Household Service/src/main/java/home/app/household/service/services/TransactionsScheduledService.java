package home.app.household.service.services;

import home.app.household.service.model.*;
import home.app.household.service.repositories.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

@Service
@Transactional
public class TransactionsScheduledService {
    @Autowired
    private HouseholdRepository householdRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void calculateBalances() {
        householdRepository.findAll().forEach(h -> {
            h.setBalance(calculateBalanceForHousehold(h));
            householdRepository.save(h);
        });
    }

    private Double calculateBalanceForHousehold(Household household) {
        Date today = new Date();

        Predicate<Transaction> extractRegularTransactions = t -> t.getClass().equals(Transaction.class) && isSameDay(today, t.getDate());
        Predicate<Transaction> extractMultipleTimesTransactions = t -> {
            if (t instanceof MultipleTimesTransaction) {
                MultipleTimesTransaction mtt = (MultipleTimesTransaction) t;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mtt.getDate());
                calendar.add(Calendar.MONTH, mtt.getNumberOfTimes().intValue());

                return isSameDay(today, calendar.getTime());
            }
            return false;
        };
        Predicate<Transaction> extractPeriodicalTransactions = t -> {
            if (t instanceof PeriodicalTransaction) {
                PeriodicalTransaction pt = (PeriodicalTransaction) t;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(pt.getDate());

                Calendar now = Calendar.getInstance();
                now.setTime(today);

                switch (pt.getPeriod()) {
                    case YEAR:
                        return calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                                calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH);
                    case MONTH:
                        return calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH);
                    default:
                        return (calendar.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR)) % 7 == 0;
                }
            }
            return false;
        };

        return household.getTransactions().stream()
                .filter(extractRegularTransactions.or(extractMultipleTimesTransactions).or(extractPeriodicalTransactions))
                .distinct()
                .reduce(household.getBalance(), (subtotal, transaction) -> {
                    if (transaction.getType().equals(TransactionType.INCOME)) {
                        return subtotal + transaction.getAmount();
                    } else {
                        return subtotal - transaction.getAmount();
                    }
                }, Double::sum);
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }
}
