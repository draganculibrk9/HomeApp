package home.app.services.service.services;

import home.app.grpc.*;
import home.app.services.service.mappers.*;
import home.app.services.service.model.Accommodation;
import home.app.services.service.model.AccommodationRequest;
import home.app.services.service.model.AccommodationType;
import home.app.services.service.model.Service;
import home.app.services.service.repositories.AccommodationRepository;
import home.app.services.service.repositories.AccommodationRequestRepository;
import home.app.services.service.repositories.ServiceRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Autowired
    private AccommodationRequestMapper accommodationRequestMapper;

    @Autowired
    private StatusMapper statusMapper;

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
    public void getServicesByAdministrator(ServiceByAdministratorRequest request, StreamObserver<ServiceResponse> responseObserver) {
        List<Service> service = serviceRepository.getAllByAdministrator(request.getAdministrator());

        service.forEach(s -> {
            ServiceResponse response = ServiceResponse.newBuilder()
                    .setService(serviceMapper.toDTO(s))
                    .build();

            responseObserver.onNext(response);
        });

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

    @Override
    public void createService(CreateOrEditServiceRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Service service = serviceMapper.toEntity(request.getService());
        service.setId(null);

        try {
            serviceRepository.save(service);
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void editService(CreateOrEditServiceRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Service service = serviceMapper.toEntity(request.getService());

        if (!serviceRepository.findById(service.getId()).isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Service with id '%d' not found", service.getId()))
                            .asRuntimeException()
            );
            return;
        }

        try {
            serviceRepository.save(service);
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteService(ServiceRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Optional<Service> service = serviceRepository.findById(request.getId());

        if (!service.isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Service with id '%d' not found", request.getId()))
                            .asRuntimeException()
            );
            return;
        }

        try {
            serviceRepository.delete(service.get());
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void createAccommodation(CreateOrEditAccommodationRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Optional<Service> s = serviceRepository.findById(request.getServiceId());

        if (!s.isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Service with id '%d' not found", request.getServiceId()))
                            .asRuntimeException()
            );
            return;
        }

        Accommodation accommodation = accommodationMapper.toEntity(request.getAccommodation());
        accommodation.setId(null);

        Service service = s.get();
        service.getAccommodations().add(accommodation);

        try {
            serviceRepository.save(service);
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void editAccommodation(CreateOrEditAccommodationRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Accommodation accommodation = accommodationMapper.toEntity(request.getAccommodation());

        if (!accommodationRepository.findById(accommodation.getId()).isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Accommodation with id '%d' not found", accommodation.getId()))
                            .asRuntimeException()
            );
            return;
        }

        try {
            accommodationRepository.save(accommodation);
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAccommodation(DeleteAccommodationRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Optional<Accommodation> a = accommodationRepository.findById(request.getAccommodationId());

        if (!a.isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Accommodation with id '%d' not found", request.getAccommodationId()))
                            .asRuntimeException()
            );
            return;
        }

        try {
            accommodationRepository.delete(a.get());
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getAccommodationRequestsForAdministrator(ServiceByAdministratorRequest request, StreamObserver<AccommodationRequestResponse> responseObserver) {
        accommodationRequestRepository.getAllByAdministrator(request.getAdministrator()).forEach(ar -> {
            AccommodationRequestResponse response = AccommodationRequestResponse.newBuilder()
                    .setAccommodationRequest(accommodationRequestMapper.toDTO(ar))
                    .build();
            responseObserver.onNext(response);
        });
        responseObserver.onCompleted();
    }

    @Override
    public void getAccommodationRequests(AccommodationRequestRequest request, StreamObserver<AccommodationRequestResponse> responseObserver) {
        accommodationRequestRepository.getAllByHousehold(request.getHouseholdId()).forEach(ar -> {
            AccommodationRequestResponse response = AccommodationRequestResponse.newBuilder()
                    .setAccommodationRequest(accommodationRequestMapper.toDTO(ar))
                    .build();
            responseObserver.onNext(response);
        });
        responseObserver.onCompleted();
    }

    @Override
    public void decideAccommodationRequest(DecideAccommodationRequestRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Optional<AccommodationRequest> ar = accommodationRequestRepository.findById(request.getAccommodationRequestId());

        if (!ar.isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Accommodation request with id '%d' not found", request.getAccommodationRequestId()))
                            .asRuntimeException()
            );
            return;
        }

        AccommodationRequest accommodationRequest = ar.get();

        if (!accommodationRequest.getStatus().equals(home.app.services.service.model.Status.PENDING)) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(String.format("Accommodation request with id '%d' has already been decided", accommodationRequest.getId()))
                            .asRuntimeException()
            );
            return;
        } else if (statusMapper.toEntity(request.getStatus()).equals(home.app.services.service.model.Status.PENDING)) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Status is already pending")
                    .asRuntimeException()
            );
            return;
        }

        accommodationRequest.setStatus(statusMapper.toEntity(request.getStatus()));
        accommodationRequestRepository.save(accommodationRequest);

        responseObserver.onNext(
                SuccessResponse.newBuilder()
                        .setSuccess(true)
                        .build()
        );
        responseObserver.onCompleted();
    }
}
