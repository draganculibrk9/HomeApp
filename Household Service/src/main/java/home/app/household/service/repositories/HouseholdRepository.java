package home.app.household.service.repositories;

import home.app.household.service.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    Household getById(Long id);

    Household getByOwner(Long owner);

    Household getByOwner(String owner);
}
