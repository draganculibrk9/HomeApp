package home.app.services.service.mappers;

import home.app.grpc.ContactMessage;
import home.app.grpc.api.mappers.AddressMapper;
import home.app.grpc.api.model.IMapper;
import home.app.services.service.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper implements IMapper<Contact, ContactMessage> {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Contact toEntity(ContactMessage dto) {
        Contact contact = new Contact();

        contact.setEmail(dto.getEmail());
        contact.setId(dto.getId());
        contact.setPhone(dto.getPhone());
        contact.setWebsite(dto.getWebsite());
        contact.setAddress(addressMapper.toEntity(dto.getAddress()));

        return contact;
    }

    @Override
    public ContactMessage toDTO(Contact contact) {
        return ContactMessage.newBuilder()
                .setAddress(addressMapper.toDTO(contact.getAddress()))
                .setEmail(contact.getEmail())
                .setId(contact.getId())
                .setPhone(contact.getPhone())
                .setWebsite(contact.getWebsite())
                .build();
    }
}
