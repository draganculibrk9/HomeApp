package home.app.services.service.mappers;

import home.app.grpc.ServiceMessage;
import home.app.grpc.api.model.IMapper;
import home.app.services.service.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ServiceMapper implements IMapper<Service, ServiceMessage> {
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public Service toEntity(ServiceMessage dto) {
        Service service = new Service();
        service.setAccommodations(new HashSet<>());
        service.setAdministrator(dto.getAdministrator());
        service.setId(dto.getId());
        service.setName(dto.getName());
        service.setContact(contactMapper.toEntity(dto.getContact()));

        return service;
    }

    @Override
    public ServiceMessage toDTO(Service service) {
        return ServiceMessage.newBuilder()
                .setAdministrator(service.getAdministrator())
                .setContact(contactMapper.toDTO(service.getContact()))
                .setId(service.getId())
                .setName(service.getName())
                .build();
    }
}
