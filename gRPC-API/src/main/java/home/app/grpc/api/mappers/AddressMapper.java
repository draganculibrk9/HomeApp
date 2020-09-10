package home.app.grpc.api.mappers;

import home.app.grpc.AddressMessage;
import home.app.grpc.api.model.Address;
import home.app.grpc.api.model.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IMapper<Address, AddressMessage> {
    @Override
    public Address toEntity(AddressMessage dto) {
        Address address = new Address();

        address.setId(dto.getId());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());

        return address;
    }

    @Override
    public AddressMessage toDTO(Address address) {
        return AddressMessage.newBuilder()
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setId(address.getId())
                .setNumber(address.getNumber())
                .setStreet(address.getStreet())
                .build();
    }
}
