package home.app.household.service.services;

import home.app.household.service.model.Household;
import home.app.household.service.model.TransactionType;
import home.app.household.service.repositories.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

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

        return household.getTransactions().stream()
                .filter(t -> isSameDay(today, t.getDate()))
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
