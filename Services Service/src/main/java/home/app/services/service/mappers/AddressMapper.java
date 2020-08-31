package home.app.services.service.mappers;

import home.app.services.service.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IMapper<Address, home.app.grpc.Address> {

    @Override
    public Address toEntity(home.app.grpc.Address dto) {
        Address address = new Address();

        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setId(dto.getId());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());

        return address;
    }

    @Override
    public home.app.grpc.Address toDTO(Address address) {
        return home.app.grpc.Address.newBuilder()
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setId(address.getId())
                .setNumber(address.getNumber())
                .setStreet(address.getStreet())
                .build();
    }
}
