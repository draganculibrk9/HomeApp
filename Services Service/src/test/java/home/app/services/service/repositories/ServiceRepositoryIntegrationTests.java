package home.app.services.service.repositories;

import home.app.services.service.model.AccommodationType;
import home.app.services.service.model.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class ServiceRepositoryIntegrationTests {
    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    public void search_noArguments_returnAll() {
        List<Service> services = serviceRepository.search("", "", null, null, null);

        assertEquals(4, services.size());
    }

    @Test
    public void search_namePassed_returnServicesContainingName() {
        String name = "ter";

        List<Service> services = serviceRepository.search(name, "", null, null, null);

        assertEquals(2, services.size());

        services.forEach(s -> assertTrue(s.getName().toLowerCase().contains(name.toLowerCase())));
    }

    @Test
    public void search_minPricePassed_returnServicesWithAtLeastOneAccommodationMoreExpensiveThanPricePassed() {
        double minPrice = 1300.0;

        List<Service> services = serviceRepository.search("", "", minPrice, null, null);

        assertEquals(3, services.size());

        services.forEach(s -> assertTrue(s.getAccommodations().stream().anyMatch(a -> a.getPrice() >= minPrice)));
    }

    @Test
    public void search_priceRangePassed_returnServicesWithAtLeastOneAccommodationInPriceRange() {
        double minPrice = 1300.0;
        double maxPrice = 1600.0;

        List<Service> services = serviceRepository.search("", "", minPrice, maxPrice, null);

        assertEquals(2, services.size());

        services.forEach(s -> assertTrue(s.getAccommodations().stream().anyMatch(a -> a.getPrice() >= minPrice && a.getPrice() <= maxPrice)));
    }

    @Test
    public void search_accommodationTypePassed_returnServicesWithAtLeastOneAccommodationOfPassedType() {
        AccommodationType accommodationType = AccommodationType.REPAIRS;

        List<Service> services = serviceRepository.search("", "", null, null, accommodationType);

        assertEquals(1, services.size());

        services.forEach(s -> assertTrue(s.getAccommodations().stream().anyMatch(a -> a.getType().equals(accommodationType))));
    }

    @Test
    public void search_allArgumentsPassed_returnServicesThatMeetAllCriteria() {
        String name = "ter";
        String city = "Singapore";
        double minPrice = 300.0;
        double maxPrice = 2000.0;
        AccommodationType accommodationType = AccommodationType.CATERING;


        List<Service> services = serviceRepository.search(name, city, minPrice, maxPrice, accommodationType);

        assertEquals(1, services.size());

        services.forEach(s -> {
            assertTrue(s.getName().toLowerCase().contains(name.toLowerCase()));
            assertTrue(s.getContact().getAddress().getCity().toLowerCase().contains(city.toLowerCase()));
            assertTrue(s.getAccommodations().stream().anyMatch(a -> a.getPrice() >= minPrice && a.getPrice() <= maxPrice));
            assertTrue(s.getAccommodations().stream().anyMatch(a -> a.getType().equals(accommodationType)));
        });
    }
}
