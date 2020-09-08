package home.app.services.service.mappers;

import home.app.grpc.AccommodationRequestMessage;
import home.app.grpc.api.model.IMapper;
import home.app.services.service.model.Accommodation;
import home.app.services.service.model.AccommodationRequest;
import home.app.services.service.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class AccommodationRequestMapper implements IMapper<AccommodationRequest, AccommodationRequestMessage> {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Override
    public AccommodationRequest toEntity(AccommodationRequestMessage dto) {
        AccommodationRequest accommodationRequest = new AccommodationRequest();

        accommodationRequest.setAccommodation(accommodationRepository.findById(dto.getAccommodation()).orElse(null));
        accommodationRequest.setFiledOn(new Date(dto.getFiledOn() * 1000));
        accommodationRequest.setHousehold(dto.getHousehold());
        accommodationRequest.setId(dto.getId());
        accommodationRequest.setRequestedFor(new Date(dto.getRequestedFor() * 1000));
        accommodationRequest.setStatus(statusMapper.toEntity(dto.getStatus()));

        return accommodationRequest;
    }

    @Override
    public AccommodationRequestMessage toDTO(AccommodationRequest accommodationRequest) {
        return AccommodationRequestMessage.newBuilder()
                .setAccommodation(accommodationRequest.getAccommodation().getId())
                .setHousehold(accommodationRequest.getHousehold())
                .setId(accommodationRequest.getId())
                .setRequestedFor(accommodationRequest.getRequestedFor().getTime())
                .setFiledOn(accommodationRequest.getFiledOn().getTime())
                .setStatus(statusMapper.toDTO(accommodationRequest.getStatus()))
                .build();
    }
}
