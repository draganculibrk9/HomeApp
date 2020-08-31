package home.app.services.service.services;

import home.app.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ServicesService extends ServicesServiceGrpc.ServicesServiceImplBase {
    @Override
    public void searchServices(SearchServiceRequest request, StreamObserver<ServiceResponse> responseObserver) {
        super.searchServices(request, responseObserver);
    }

    @Override
    public void getService(ServiceRequest request, StreamObserver<ServiceResponse> responseObserver) {
        super.getService(request, responseObserver);
    }

    @Override
    public void getServiceByAdministrator(ServiceByAdministratorRequest request, StreamObserver<ServiceResponse> responseObserver) {
        super.getServiceByAdministrator(request, responseObserver);
    }

    @Override
    public void getAccommodations(ServiceRequest request, StreamObserver<AccommodationResponse> responseObserver) {
        super.getAccommodations(request, responseObserver);
    }
}
