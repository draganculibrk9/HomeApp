package home.app.services.service.mappers;

import home.app.grpc.AccommodationMessage;
import home.app.services.service.model.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationMapper implements IMapper<Accommodation, AccommodationMessage> {
    @Autowired
    private AccommodationTypeMapper accommodationTypeMapper;

    @Override
    public Accommodation toEntity(AccommodationMessage dto) {
        Accommodation accommodation = new Accommodation();

        accommodation.setAvailable(dto.getAvailable());
        accommodation.setId(dto.getId());
        accommodation.setName(dto.getName());
        accommodation.setPrice(dto.getPrice());
        accommodation.setType(accommodationTypeMapper.toEntity(dto.getType()));

        return accommodation;
    }

    @Override
    public AccommodationMessage toDTO(Accommodation accommodation) {
        return AccommodationMessage.newBuilder()
                .setAvailable(accommodation.getAvailable())
                .setId(accommodation.getId())
                .setName(accommodation.getName())
                .setPrice(accommodation.getPrice())
                .setType(accommodationTypeMapper.toDTO(accommodation.getType()))
                .build();
    }
}
