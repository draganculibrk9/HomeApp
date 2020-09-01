package home.app.services.service.mappers;

import home.app.grpc.AccommodationMessage;
import home.app.grpc.api.model.IMapper;
import home.app.services.service.model.AccommodationType;
import org.springframework.stereotype.Component;

@Component
public class AccommodationTypeMapper implements IMapper<AccommodationType, AccommodationMessage.AccommodationType> {
    @Override
    public AccommodationType toEntity(AccommodationMessage.AccommodationType dto) {
        switch (dto) {
            case HYGIENE:
                return AccommodationType.HYGIENE;
            case REPAIRS:
                return AccommodationType.REPAIRS;
            default:
                return AccommodationType.CATERING;
        }
    }

    @Override
    public AccommodationMessage.AccommodationType toDTO(AccommodationType accommodationType) {
        switch (accommodationType) {
            case CATERING:
                return AccommodationMessage.AccommodationType.CATERING;
            case REPAIRS:
                return AccommodationMessage.AccommodationType.REPAIRS;
            case HYGIENE:
                return AccommodationMessage.AccommodationType.HYGIENE;
            default:
                return AccommodationMessage.AccommodationType.UNKNOWN;
        }
    }
}
