package home.app.services.service.mappers;

import home.app.grpc.AccommodationRequestMessage;
import home.app.grpc.api.model.IMapper;
import home.app.services.service.model.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper implements IMapper<Status, AccommodationRequestMessage.Status> {
    @Override
    public Status toEntity(AccommodationRequestMessage.Status dto) {
        switch (dto) {
            case PENDING:
                return Status.PENDING;
            case ACCEPTED:
                return Status.REJECTED;
            default:
                return Status.ACCEPTED;
        }
    }

    @Override
    public AccommodationRequestMessage.Status toDTO(Status status) {
        switch (status) {
            case ACCEPTED:
                return AccommodationRequestMessage.Status.ACCEPTED;
            case PENDING:
                return AccommodationRequestMessage.Status.PENDING;
            default:
                return AccommodationRequestMessage.Status.REJECTED;
        }
    }
}
