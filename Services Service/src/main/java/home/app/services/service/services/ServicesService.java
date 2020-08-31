package home.app.services.service.services;

import home.app.grpc.*;
import home.app.services.service.mappers.AccommodationMapper;
import home.app.services.service.mappers.AccommodationTypeMapper;
import home.app.services.service.mappers.ServiceMapper;
import home.app.services.service.model.AccommodationType;
import home.app.services.service.model.Service;
import home.app.services.service.repositories.ServiceRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@Transactional
public class ServicesService extends ServicesServiceGrpc.ServicesServiceImplBase {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private AccommodationMapper accommodationMapper;

    @Autowired
    private AccommodationTypeMapper accommodationTypeMapper;

    @Override
    public void searchServices(SearchServiceRequest request, StreamObserver<ServiceResponse> responseObserver) {
        String name = (request.hasField(request.getDescriptorForType().findFieldByName("name")))
                ? request.getName() : "";
        String city = (request.hasField(request.getDescriptorForType().findFieldByName("city")))
                ? request.getCity() : "";
        Double maximumPrice = (request.hasField(request.getDescriptorForType().findFieldByName("maximum_price")))
                ? request.getMaximumPrice() : null;
        Double minimumPrice = (request.hasField(request.getDescriptorForType().findFieldByName("minimum_price")))
                ? request.getMinimumPrice() : null;
        AccommodationType type = (request.hasField(request.getDescriptorForType().findFieldByName("type")))
                ? accommodationTypeMapper.toEntity(request.getType()) : null;

        serviceRepository.search(name, city, minimumPrice, maximumPrice, type).forEach(s -> {
            ServiceResponse response = ServiceResponse.newBuilder()
                    .setService(serviceMapper.toDTO(s))
                    .build();

            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }

    @Override
    public void getService(ServiceRequest request, StreamObserver<ServiceResponse> responseObserver) {
        Service service = serviceRepository.getById(request.getId());

        if (service == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Service with id '%d' not found", request.getId()))
                            .asRuntimeException()
            );
            return;
        }

        ServiceResponse response = ServiceResponse.newBuilder()
                .setService(serviceMapper.toDTO(service))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getServiceByAdministrator(ServiceByAdministratorRequest request, StreamObserver<ServiceResponse> responseObserver) {
        Service service = serviceRepository.getByAdministrator(request.getAdministrator());

        if (service == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Service with administrator '%s' not found", request.getAdministrator()))
                            .asRuntimeException()
            );
            return;
        }

        ServiceResponse response = ServiceResponse.newBuilder()
                .setService(serviceMapper.toDTO(service))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAccommodations(ServiceRequest request, StreamObserver<AccommodationResponse> responseObserver) {
        Service service = serviceRepository.getById(request.getId());

        if (service == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Service with id '%d' not found", request.getId()))
                            .asRuntimeException()
            );
            return;
        }

        service.getAccommodations().forEach(a -> {
            AccommodationResponse response = AccommodationResponse.newBuilder()
                    .setAccommodation(accommodationMapper.toDTO(a))
                    .build();

            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }
}
