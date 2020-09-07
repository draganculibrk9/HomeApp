package home.app.services.service.repositories;

import home.app.services.service.model.AccommodationRequest;
import home.app.services.service.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationRequestRepository extends JpaRepository<AccommodationRequest, Long> {
    List<AccommodationRequest> getAllByHousehold(Long household);

    @Query(value = "select ar from AccommodationRequest ar where :administrator = (select s.administrator from Service s where ar.accommodation member of s.accommodations and ar.status = :status)")
    List<AccommodationRequest> getAllByAdministrator(@Param("administrator") String administrator, @Param("status") Status status);
}
