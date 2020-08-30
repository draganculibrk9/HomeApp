package home.app.household.service.repositories;

import home.app.household.service.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    Household getById(Long id);

    Household getByOwner(Long owner);

    Household getByOwner(String owner);

    @Query("select h from Household h join h.transactions b where b.id = :transaction_id")
    Household getByTransactionId(@Param("transaction_id") Long transactionId);
}
