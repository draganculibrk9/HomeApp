package home.app.auth.service.mappers;

import home.app.auth.service.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IMapper<Address, home.app.grpc.Address> {
    @Override
    public Address toEntity(home.app.grpc.Address dto) {
        Address address = new Address();
        address.setId(null);
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());

        return address;
    }

    @Override
    public home.app.grpc.Address toDTO(Address address) {
        throw new UnsupportedOperationException();
    }
}
