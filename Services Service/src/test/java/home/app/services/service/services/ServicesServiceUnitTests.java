package home.app.services.service.services;

import home.app.grpc.*;
import home.app.grpc.api.model.Address;
import home.app.services.service.mappers.*;
import home.app.services.service.model.*;
import home.app.services.service.repositories.AccommodationRepository;
import home.app.services.service.repositories.AccommodationRequestRepository;
import home.app.services.service.repositories.ServiceRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.testing.StreamRecorder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext
public class ServicesServiceUnitTests {
    @Autowired
    private ServicesService servicesService;

    @MockBean
    private ServiceRepository serviceRepository;

    @MockBean
    private ServiceMapper serviceMapper;

    @MockBean
    private AccommodationMapper accommodationMapper;

    @MockBean
    private AccommodationTypeMapper accommodationTypeMapper;

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private AccommodationRequestRepository accommodationRequestRepository;

    @MockBean
    private AccommodationRequestMapper accommodationRequestMapper;

    @MockBean
    private StatusMapper statusMapper;

    @GrpcClient("test")
    private HouseholdServiceGrpc.HouseholdServiceBlockingStub householdServiceStub;

    @Test
    public void getService_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        when(serviceRepository.getById(id)).thenReturn(null);

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
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        Service service = Service.builder()
                .accommodations(new HashSet<>())
                .administrator("admin@admin.com")
                .contact(new Contact())
                .id(id)
                .name("service")
                .build();

        when(serviceRepository.getById(id)).thenReturn(service);

        ServiceMessage serviceMessage = ServiceMessage.newBuilder()
                .setAdministrator(service.getAdministrator())
                .setContact(ContactMessage.newBuilder().build())
                .setId(service.getId())
                .setName(service.getName())
                .build();

        when(serviceMapper.toDTO(service)).thenReturn(serviceMessage);

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
        assertEquals(serviceMessage, response.getService());
    }

    @Test
    public void getAccommodations_serviceDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        when(serviceRepository.getById(id)).thenReturn(null);

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
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        Accommodation a1 = Accommodation.builder()
                .available(true)
                .id(0L)
                .name("a1")
                .price(300.0)
                .type(AccommodationType.CATERING)
                .build();

        Accommodation a2 = Accommodation.builder()
                .available(true)
                .id(1L)
                .name("a2")
                .price(360.0)
                .type(AccommodationType.CATERING)
                .build();

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(a1);
        accommodations.add(a2);

        Service service = Service.builder()
                .name("service")
                .id(id)
                .contact(new Contact())
                .administrator("admin@admin.com")
                .accommodations(accommodations)
                .build();

        when(serviceRepository.getById(id)).thenReturn(service);

        AccommodationMessage am1 = AccommodationMessage.newBuilder()
                .setType(AccommodationMessage.AccommodationType.CATERING)
                .setPrice(a1.getPrice())
                .setName(a1.getName())
                .setId(a1.getId())
                .setAvailable(a1.getAvailable())
                .build();

        AccommodationMessage am2 = AccommodationMessage.newBuilder()
                .setType(AccommodationMessage.AccommodationType.CATERING)
                .setPrice(a2.getPrice())
                .setName(a2.getName())
                .setId(a2.getId())
                .setAvailable(a2.getAvailable())
                .build();

        when(accommodationMapper.toDTO(a1)).thenReturn(am1);
        when(accommodationMapper.toDTO(a2)).thenReturn(am2);

        AccommodationResponse ar1 = AccommodationResponse.newBuilder()
                .setAccommodation(am1)
                .build();

        AccommodationResponse ar2 = AccommodationResponse.newBuilder()
                .setAccommodation(am2)
                .build();

        StreamRecorder<AccommodationResponse> responseObserver = StreamRecorder.create();

        servicesService.getAccommodations(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(2, responseObserver.getValues().size());
        assertTrue(responseObserver.getValues().contains(ar1));
        assertTrue(responseObserver.getValues().contains(ar2));
    }

    @Test
    public void createService_everythingOk_ServiceResponseReturned() throws Exception {
        ServiceMessage serviceMessage = ServiceMessage.newBuilder()
                .setName("service")
                .setContact(ContactMessage.newBuilder().setAddress(AddressMessage.newBuilder().build()).build())
                .setAdministrator("admin@admin.com")
                .build();

        CreateOrEditServiceRequest request = CreateOrEditServiceRequest.newBuilder()
                .setService(serviceMessage)
                .build();

        Service beforeSave = Service.builder()
                .accommodations(new HashSet<>())
                .administrator(serviceMessage.getAdministrator())
                .contact(Contact.builder().address(Address.builder().build()).build())
                .name(serviceMessage.getName())
                .build();

        Service afterSave = beforeSave.toBuilder()
                .id(0L)
                .build();

        ServiceMessage returnValue = serviceMessage.toBuilder()
                .setId(afterSave.getId())
                .build();

        when(serviceMapper.toEntity(serviceMessage)).thenReturn(beforeSave);
        when(serviceRepository.save(beforeSave)).thenReturn(afterSave);
        when(serviceMapper.toDTO(afterSave)).thenReturn(returnValue);

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

        assertEquals(returnValue, responseMessage);
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

        Service service = Service.builder()
                .id(serviceMessage.getId())
                .accommodations(new HashSet<>())
                .administrator(serviceMessage.getAdministrator())
                .contact(Contact.builder().address(Address.builder().build()).build())
                .name(serviceMessage.getName())
                .build();

        CreateOrEditServiceRequest request = CreateOrEditServiceRequest.newBuilder()
                .setService(serviceMessage)
                .build();

        when(serviceMapper.toEntity(serviceMessage)).thenReturn(service);
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

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
    public void editService_everythingOk_ServiceResponseReturned() throws Exception {
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

        Service beforeEdit = Service.builder()
                .id(id)
                .accommodations(new HashSet<>())
                .administrator(serviceMessage.getAdministrator())
                .contact(Contact.builder().address(Address.builder().build()).build())
                .name("other name")
                .build();

        Service afterEdit = beforeEdit.toBuilder()
                .name(serviceMessage.getName())
                .build();

        when(serviceMapper.toEntity(serviceMessage)).thenReturn(afterEdit);
        when(serviceRepository.findById(id)).thenReturn(Optional.of(beforeEdit));
        when(serviceRepository.save(afterEdit)).thenReturn(afterEdit);
        when(serviceMapper.toDTO(afterEdit)).thenReturn(serviceMessage);

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

        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

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
    public void deleteService_serviceDoesExist_SuccessResponseReturned() throws Exception {
        long id = 0L;

        ServiceRequest request = ServiceRequest.newBuilder()
                .setId(id)
                .build();

        Service service = Service.builder()
                .id(id)
                .name("service")
                .contact(Contact.builder().address(Address.builder().build()).build())
                .administrator("admin@admin.com")
                .accommodations(new HashSet<>())
                .build();

        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));

        StreamRecorder<SuccessResponse> responseObserver = StreamRecorder.create();

        servicesService.deleteService(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertNotNull(responseObserver.getValues());
        assertEquals(1, responseObserver.getValues().size());

        verify(serviceRepository, times(1)).delete(service);
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

        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

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
    public void createAccommodation_serviceDoesExist_AccommodationResponseReturned() throws Exception {
        long id = 0L;

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

        Service service = Service.builder()
                .accommodations(new HashSet<>())
                .administrator("admin@admin.com")
                .contact(Contact.builder().build())
                .name("service")
                .id(id)
                .build();

        Accommodation accommodationBeforeSave = Accommodation.builder()
                .type(AccommodationType.CATERING)
                .price(500.0)
                .name("accommodation")
                .available(true)
                .build();

        Accommodation accommodatioAfterSave = accommodationBeforeSave.toBuilder()
                .id(0L)
                .build();

        AccommodationMessage responseMessage = accommodationMessage.toBuilder()
                .setId(0L)
                .build();

        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(accommodationMapper.toEntity(accommodationMessage)).thenReturn(accommodationBeforeSave);
        when(accommodationRepository.save(accommodationBeforeSave)).thenReturn(accommodatioAfterSave);
        when(accommodationMapper.toDTO(accommodatioAfterSave)).thenReturn(responseMessage);

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

        verify(serviceRepository, times(1)).save(service);

        AccommodationMessage responseAccommodation = response.getAccommodation();
        assertNotNull(responseAccommodation);

        assertEquals(responseMessage, responseAccommodation);
    }

    @Test
    public void editAccommodation_accommodationDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        AccommodationMessage accommodationMessage = AccommodationMessage.newBuilder()
                .setId(id)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .id(id)
                .build();

        CreateOrEditAccommodationRequest request = CreateOrEditAccommodationRequest.newBuilder()
                .setAccommodation(accommodationMessage)
                .build();

        when(accommodationMapper.toEntity(accommodationMessage)).thenReturn(accommodation);
        when(accommodationRepository.findById(id)).thenReturn(Optional.empty());

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
    public void editAccommodation_accommodationDoesExist_AccommodationResponseReturned() throws Exception {
        long id = 0L;

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

        Accommodation accommodationBeforeSave = Accommodation.builder()
                .type(AccommodationType.CATERING)
                .price(500.0)
                .name("accommodation1")
                .available(true)
                .id(id)
                .build();

        Accommodation accommodatioAfterSave = accommodationBeforeSave.toBuilder()
                .name(accommodationMessage.getName())
                .build();

        when(accommodationMapper.toEntity(accommodationMessage)).thenReturn(accommodatioAfterSave);
        when(accommodationRepository.findById(id)).thenReturn(Optional.of(accommodationBeforeSave));
        when(accommodationRepository.save(accommodatioAfterSave)).thenReturn(accommodatioAfterSave);
        when(accommodationMapper.toDTO(accommodatioAfterSave)).thenReturn(accommodationMessage);

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

        when(serviceRepository.findById(service_id)).thenReturn(Optional.empty());

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
        long service_id = 0L;
        long accommodation_id = 0L;

        DeleteAccommodationRequest request = DeleteAccommodationRequest.newBuilder()
                .setServiceId(service_id)
                .setAccommodationId(accommodation_id)
                .build();

        Service service = Service.builder()
                .id(service_id)
                .name("service")
                .contact(Contact.builder().build())
                .administrator("admin@admin.com")
                .accommodations(new HashSet<>())
                .build();

        when(serviceRepository.findById(service_id)).thenReturn(Optional.of(service));

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
    public void deleteAccommodation_serviceAndAccommodatonDoExist_SuccessResponseReturned() throws Exception {
        long service_id = 0L;
        long accommodation_id = 0L;

        DeleteAccommodationRequest request = DeleteAccommodationRequest.newBuilder()
                .setServiceId(service_id)
                .setAccommodationId(accommodation_id)
                .build();

        Accommodation accommodation = Accommodation.builder()
                .name("accommodation")
                .id(accommodation_id)
                .available(true)
                .price(300.0)
                .type(AccommodationType.REPAIRS)
                .build();

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);

        Service service = Service.builder()
                .id(service_id)
                .name("service")
                .contact(Contact.builder().build())
                .administrator("admin@admin.com")
                .accommodations(accommodations)
                .build();

        when(serviceRepository.findById(service_id)).thenReturn(Optional.of(service));

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
        verify(serviceRepository, times(1)).save(service);
        assertTrue(successResponse.getSuccess());
    }

    @Test
    public void decideAccommodationRequest_accommodationRequestDoesNotExist_NotFoundStatusReturned() throws Exception {
        long id = 0L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.ACCEPTED)
                .build();

        when(accommodationRequestRepository.findById(id)).thenReturn(Optional.empty());

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
        long id = 0L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.ACCEPTED)
                .build();

        AccommodationRequest accommodationRequest = AccommodationRequest.builder()
                .accommodation(Accommodation.builder().build())
                .filedOn(new Date())
                .household(0L)
                .id(id)
                .requestedFor(new Date())
                .status(home.app.services.service.model.Status.REJECTED)
                .build();

        when(accommodationRequestRepository.findById(id)).thenReturn(Optional.of(accommodationRequest));

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
        long id = 0L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.PENDING)
                .build();

        AccommodationRequest accommodationRequest = AccommodationRequest.builder()
                .accommodation(Accommodation.builder().build())
                .filedOn(new Date())
                .household(0L)
                .id(id)
                .requestedFor(new Date())
                .status(home.app.services.service.model.Status.PENDING)
                .build();

        when(accommodationRequestRepository.findById(id)).thenReturn(Optional.of(accommodationRequest));
        when(statusMapper.toEntity(request.getStatus())).thenReturn(home.app.services.service.model.Status.PENDING);

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
    public void decideAccommodationRequest_everythingOk_SuccessResponseReturned() throws Exception {
        long id = 0L;

        DecideAccommodationRequestRequest request = DecideAccommodationRequestRequest.newBuilder()
                .setAccommodationRequestId(id)
                .setStatus(AccommodationRequestMessage.Status.ACCEPTED)
                .build();

        AccommodationRequest accommodationRequest = AccommodationRequest.builder()
                .accommodation(Accommodation.builder().build())
                .filedOn(new Date())
                .household(0L)
                .id(id)
                .requestedFor(new Date())
                .status(home.app.services.service.model.Status.PENDING)
                .build();

        when(accommodationRequestRepository.findById(id)).thenReturn(Optional.of(accommodationRequest));
        when(statusMapper.toEntity(request.getStatus())).thenReturn(home.app.services.service.model.Status.ACCEPTED);

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
        verify(accommodationRequestRepository, times(1)).save(accommodationRequest);
        assertTrue(response.getSuccess());
    }
    // TODO: requestAccommodation in future issue
}
