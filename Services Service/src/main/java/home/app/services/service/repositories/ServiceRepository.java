package home.app.services.service.repositories;

import home.app.services.service.model.AccommodationType;
import home.app.services.service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service getById(Long id);

    List<Service> getAllByAdministrator(String administrator);

    @Query(value = "select s from Service s where lower(s.name) like lower(concat('%', :name, '%'))" +
            " and lower(s.contact.address.city) like lower(concat('%', :city, '%'))" +
            "and (:min_price is null or exists(select true from s.accommodations a where a.price >= :min_price))" +
            "and (:max_price is null or exists(select true from s.accommodations a where a.price <= :max_price))" +
            "and (:type is null or exists(select true from s.accommodations a where a.type = :type))")
    List<Service> search(@Param("name") String name, @Param("city") String city,
                         @Param("min_price") Double minimumPrice, @Param("max_price") Double maximumPrice,
                         @Param("type") AccommodationType accommodationType);
}
