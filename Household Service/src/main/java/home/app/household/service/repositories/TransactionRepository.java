package home.app.household.service.repositories;

import home.app.household.service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction getById(Long id);
}
