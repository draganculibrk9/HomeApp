package home.app.services.service.services;

import home.app.services.service.model.Status;
import home.app.services.service.repositories.AccommodationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccommodationRequestScheduledService {
    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Scheduled(cron = "0 0 0 * * *")
    private void rejectExpiredAccommodationRequests() {
        accommodationRequestRepository.getAllUnresolvedAndExpired(Status.PENDING).forEach(ar -> {
            ar.setStatus(Status.REJECTED);
            accommodationRequestRepository.save(ar);
        });
    }
}
