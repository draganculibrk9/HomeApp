package home.app.services.service.services;

import home.app.grpc.*;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class ServicesServiceIntegrationTests {
    @Autowired
    private ServicesService servicesService;

    @Rule
    private final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Autowired
    private GrpcChannelsProperties grpcChannelsProperties;

    @Test
    public void getService_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Service with id '%d' not found", id)));

        StreamRecorder<ServiceResponse> responseObserver = StreamRecorder.create();

        servicesService.getService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void getService_serviceDoesExist_ServiceResponseReturned() throws Exception {
        long id = 21L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        StreamRecorder<ServiceResponse> responseObserver = StreamRecorder.create();

        servicesService.getService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        ServiceResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertNotNull(response.getService());
        assertEquals(id, response.getService().getId());
    }

    @Test
    public void getAccommodations_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Service with id '%d' not found", id)));

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.getAccommodations(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void getAccommodations_serviceDoesExist_AccommodationResponsesReturned() throws Exception {
        long id = 21L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.getAccommodations(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(2, responseObserver.getValues().size());

        responseObserver.getValues().forEach(ar -> assertNotNull(ar.getAccommodation()));
    }

    @Test
    @Transactional
    @Rollback
    public void createService_everythingOk_ServiceResponseReturned() throws Exception {
        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setStreet("street")
                .setNumber(1)
                .setCountry("serbia")
                .setCity("city")
                .build();

        ContactMessage contactMessage = ContactMessage.newBuilder()
                .setAddress(addressMessage)
                .setEmail("email@email.com")
                .setPhone("+387 074439696")
                .setWebsite("www.site.rs")
                .build();

        ServiceMessage serviceMessage = ServiceMessage.newBuilder()
                .setName("service")
                .setContact(contactMessage)
                .setAdministrator("admin@admin.com")
                .build();

        CreateOrEditServiceRequest request = CreateOrEditServiceRequest.newBuilder()
                .setService(serviceMessage)
                .build();

        StreamRecorder<ServiceResponse> responseObserver = StreamRecorder.create();

        servicesService.createService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        ServiceResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);

        ServiceMessage responseMessage = response.getService();
        assertNotNull(responseMessage);
    }

    @Test
    public void editService_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ServiceMessage serviceMessage = ServiceMessage.newBuilder()
                .setId(id)
                .setName("service")
                .setContact(ContactMessage.newBuilder().setAddress(AddressMessage.newBuilder().build()).build())
                .setAdministrator("admin@admin.com")
                .build();

        CreateOrEditServiceRequest request = CreateOrEditServiceRequest.newBuilder()
                .setService(serviceMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Service with id '%d' not found", id)));

        StreamRecorder<ServiceResponse> responseObserver = StreamRecorder.create();

        servicesService.editService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void editService_everythingOk_ServiceResponseReturned() throws Exception {
        long id = 21L;

        AddressMessage addressMessage = AddressMessage.newBuilder()
                .setId(13L)
                .setStreet("street")
                .setNumber(13)
                .setCountry("serbia")
                .setCity("city")
                .build();

        ContactMessage contactMessage = ContactMessage.newBuilder()
                .setId(17L)
                .setAddress(addressMessage)
                .setEmail("email@email.com")
                .setPhone("+387 074439696")
                .setWebsite("www.site.rs")
                .build();

        ServiceMessage serviceMessage = ServiceMessage.newBuilder()
                .setId(id)
                .setName("service")
                .setContact(contactMessage)
                .setAdministrator("admin@admin.com")
                .build();

        CreateOrEditServiceRequest request = CreateOrEditServiceRequest.newBuilder()
                .setService(serviceMessage)
                .build();

        StreamRecorder<ServiceResponse> responseObserver = StreamRecorder.create();

        servicesService.editService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        ServiceResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);

        ServiceMessage responseMessage = response.getService();
        assertNotNull(responseMessage);

        assertEquals(serviceMessage, responseMessage);
    }

    @Test
    public void deleteService_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Service with id '%d' not found", id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.deleteService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteService_serviceDoesExist_SuccessResponseReturned() throws Exception {
        long id = 21L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.deleteService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void createAccommodation_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        CreateOrEditAccommodationRequest request = CreateOrEditAccommodationRequest.newBuilder()
                .setServiceId(id)
                .setAccommodation(AccommodationMessage.newBuilder().build())
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Service with id '%d' not found", id)));

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.createAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void createAccommodation_serviceDoesExist_AccommodationResponseReturned() throws Exception {
        long id = 21L;

        AccommodationMessage accommodationMessage = AccommodationMessage.newBuilder()
                .setAvailable(true)
                .setName("accommodation")
                .setPrice(500.0)
                .setType(AccommodationMessage.AccommodationType.CATERING)
                .build();

        CreateOrEditAccommodationRequest request = CreateOrEditAccommodationRequest.newBuilder()
                .setServiceId(id)
                .setAccommodation(accommodationMessage)
                .build();

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.createAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        AccommodationResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);


        AccommodationMessage responseAccommodation = response.getAccommodation();
        assertNotNull(responseAccommodation);
    }

    @Test
    public void editAccommodation_accommodationDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        AccommodationMessage accommodationMessage = AccommodationMessage.newBuilder()
                .setId(id)
                .setType(AccommodationMessage.AccommodationType.CATERING)
                .setPrice(300.0)
                .setName("edited")
                .setAvailable(false)
                .build();

        CreateOrEditAccommodationRequest request = CreateOrEditAccommodationRequest.newBuilder()
                .setAccommodation(accommodationMessage)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Accommodation with id '%d' not found", id)));

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.editAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void editAccommodation_accommodationDoesExist_AccommodationResponseReturned() throws Exception {
        long id = 25L;

        AccommodationMessage accommodationMessage = AccommodationMessage.newBuilder()
                .setId(id)
                .setAvailable(true)
                .setName("accommodation")
                .setPrice(500.0)
                .setType(AccommodationMessage.AccommodationType.CATERING)
                .build();

        CreateOrEditAccommodationRequest request = CreateOrEditAccommodationRequest.newBuilder()
                .setAccommodation(accommodationMessage)
                .build();

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.editAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        AccommodationResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);

        AccommodationMessage responseAccommodation = response.getAccommodation();
        assertNotNull(responseAccommodation);

        assertEquals(accommodationMessage, responseAccommodation);
    }

    @Test
    public void deleteAccommodation_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long service_id = 0L;
        long accommodation_id = 0L;

        DeleteAccommodationRequest request = DeleteAccommodationRequest.newBuilder()
                .setServiceId(service_id)
                .setAccommodationId(accommodation_id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Service with id '%d' not found", service_id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.deleteAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void deleteAccommodation_accommodationDoesNotExist_NotFoundStatusReturned() throws Exception {
        long service_id = 21L;
        long accommodation_id = 0L;

        DeleteAccommodationRequest request = DeleteAccommodationRequest.newBuilder()
                .setServiceId(service_id)
                .setAccommodationId(accommodation_id)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Accommodation with id '%d' not found", accommodation_id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.deleteAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteAccommodation_serviceAndAccommodatonDoExist_SuccessResponseReturned() throws Exception {
        long service_id = 21L;
        long accommodation_id = 25L;

        DeleteAccommodationRequest request = DeleteAccommodationRequest.newBuilder()
                .setServiceId(service_id)
                .setAccommodationId(accommodation_id)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.deleteAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse successResponse = responseObserver.getValues().get(0);
        assertNotNull(successResponse);
        assertTrue(successResponse.getSuccess());
    }

    @Test
    public void decideAccommodationRequest_accommodationRequestDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.ACCEPTED)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Accommodation request with id '%d' not found", id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.decideAccommodationRequest(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void decideAccommodationRequest_accommodationRequestIsNotPending_InvalidArgumentStatusReturned() throws Exception {
        long id = 32L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.ACCEPTED)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(String.format("Accommodation request with id '%d' has already been decided", id)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.decideAccommodationRequest(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    public void decideAccommodationRequest_sentStatusIsPending_InvalidArgumentStatusReturned() throws Exception {
        long id = 30L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.PENDING)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Status is already pending"));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.decideAccommodationRequest(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());
    }

    @Test
    @Transactional
    @Rollback
    public void decideAccommodationRequest_everythingOk_SuccessResponseReturned() throws Exception {
        long id = 30L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.ACCEPTED)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.decideAccommodationRequest(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void requestAccommodation_householdDoesNotExist_NotFoundStatusReturned() throws Exception {
        HouseholdServiceGrpc.HouseholdServiceImplBase householdServiceImplBase = mock(HouseholdServiceGrpc.HouseholdServiceImplBase.class,
                AdditionalAnswers.delegatesTo(
                        new HouseholdServiceGrpc.HouseholdServiceImplBase() {
                            @Override
                            public void getHousehold(HouseholdRequest request, StreamObserver<HouseholdResponse> responseObserver) {
                                responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(String.format("Household with owner '%s' not found", request.getOwner()))));
                            }
                        }
                ));

        Server server = grpcCleanup.register(
                InProcessServerBuilder
                        .forName(grpcChannelsProperties.getChannel("householdService").getAddress().getSchemeSpecificPart())
                        .directExecutor()
                        .addService(householdServiceImplBase)
                        .build()
                        .start()
        );
        AccommodationRequestMessage accommodationRequestMessage = AccommodationRequestMessage.newBuilder()
                .setAccommodation(0L)
                .setFiledOn(new Date().getTime())
                .setHousehold(0L)
                .setRequestedFor(new Date().getTime())
                .build();

        String owner = "owner@owner.com";

        RequestAccommodationRequest request = RequestAccommodationRequest.newBuilder()
                .setAccommodationRequest(accommodationRequestMessage)
                .setOwner(owner)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(String.format("Household with owner '%s' not found", owner)));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.requestAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());

        server.shutdown().awaitTermination();
    }

    @Test
    public void requestAccommodation_accommodationDoesNotExist_NotFoundStatusReturned() throws Exception {
        HouseholdServiceGrpc.HouseholdServiceImplBase householdServiceImplBase = mock(HouseholdServiceGrpc.HouseholdServiceImplBase.class,
                AdditionalAnswers.delegatesTo(
                        new HouseholdServiceGrpc.HouseholdServiceImplBase() {
                            @Override
                            public void getHousehold(HouseholdRequest request, StreamObserver<HouseholdResponse> responseObserver) {
                                HouseholdMessage householdMessage = HouseholdMessage.newBuilder()
                                        .setOwner(request.getOwner())
                                        .setId(0L)
                                        .setBalance(0.0)
                                        .build();

                                HouseholdResponse householdResponse = HouseholdResponse.newBuilder()
                                        .setHousehold(householdMessage)
                                        .build();

                                responseObserver.onNext(householdResponse);
                                responseObserver.onCompleted();
                            }
                        }
                ));

        Server server = grpcCleanup.register(
                InProcessServerBuilder
                        .forName(grpcChannelsProperties.getChannel("householdService").getAddress().getSchemeSpecificPart())
                        .directExecutor()
                        .addService(householdServiceImplBase)
                        .build()
                        .start()
        );
        AccommodationRequestMessage accommodationRequestMessage = AccommodationRequestMessage.newBuilder()
                .setAccommodation(0L)
                .setFiledOn(new Date().getTime())
                .setHousehold(0L)
                .setRequestedFor(new Date().getTime())
                .build();

        String owner = "owner@owner.com";

        RequestAccommodationRequest request = RequestAccommodationRequest.newBuilder()
                .setAccommodationRequest(accommodationRequestMessage)
                .setOwner(owner)
                .build();

        StatusRuntimeException expected = new StatusRuntimeException(Status.NOT_FOUND.withDescription(String.format("Accommodation with id '%d' not found", request.getAccommodationRequest().getAccommodation())));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.requestAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNotNull(responseObserver.getError());
        assertEquals(StatusRuntimeException.class, responseObserver.getError().getClass());
        assertEquals(expected.getStatus().getCode(), ((StatusRuntimeException) responseObserver.getError()).getStatus().getCode());
        assertEquals(expected.getMessage(), responseObserver.getError().getMessage());

        server.shutdown().awaitTermination();
    }

    @Test
    @Transactional
    @Rollback
    public void requestAccommodation_everythingOk_SuccessResponseReturned() throws Exception {
        HouseholdServiceGrpc.HouseholdServiceImplBase householdServiceImplBase = mock(HouseholdServiceGrpc.HouseholdServiceImplBase.class,
                AdditionalAnswers.delegatesTo(
                        new HouseholdServiceGrpc.HouseholdServiceImplBase() {
                            @Override
                            public void getHousehold(HouseholdRequest request, StreamObserver<HouseholdResponse> responseObserver) {
                                HouseholdMessage householdMessage = HouseholdMessage.newBuilder()
                                        .setOwner(request.getOwner())
                                        .setId(0L)
                                        .setBalance(0.0)
                                        .build();

                                HouseholdResponse householdResponse = HouseholdResponse.newBuilder()
                                        .setHousehold(householdMessage)
                                        .build();

                                responseObserver.onNext(householdResponse);
                                responseObserver.onCompleted();
                            }
                        }
                ));

        Server server = grpcCleanup.register(
                InProcessServerBuilder
                        .forName(grpcChannelsProperties.getChannel("householdService").getAddress().getSchemeSpecificPart())
                        .directExecutor()
                        .addService(householdServiceImplBase)
                        .build()
                        .start()
        );

        AccommodationRequestMessage accommodationRequestMessage = AccommodationRequestMessage.newBuilder()
                .setAccommodation(25L)
                .setFiledOn(new Date().getTime())
                .setHousehold(0L)
                .setRequestedFor(new Date().getTime())
                .build();

        String owner = "owner@owner.com";

        RequestAccommodationRequest request = RequestAccommodationRequest.newBuilder()
                .setAccommodationRequest(accommodationRequestMessage)
                .setOwner(owner)
                .build();

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.requestAccommodation(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        SuccessResponse response = responseObserver.getValues().get(0);
        assertNotNull(response);
        assertTrue(response.getSuccess());

        server.shutdown().awaitTermination();
    }
}
