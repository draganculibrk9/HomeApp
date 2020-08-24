package home.app.household.service.services;

import home.app.grpc.*;
import home.app.household.service.mappers.HouseholdMapper;
import home.app.household.service.mappers.TransactionMapper;
import home.app.household.service.model.Household;
import home.app.household.service.model.Transaction;
import home.app.household.service.repositories.HouseholdRepository;
import home.app.household.service.repositories.TransactionRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class HouseholdService extends HouseholdServiceGrpc.HouseholdServiceImplBase {
    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private HouseholdMapper householdMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void getHousehold(HouseholdRequest request, StreamObserver<HouseholdResponse> responseObserver) {
        String ownerEmail = request.getOwner();

        Household household = householdRepository.getByOwner(ownerEmail);

        if (household == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Household with owner '%s' not found", ownerEmail))
                            .asRuntimeException()
            );
            return;
        }
        HouseholdResponse response = HouseholdResponse.newBuilder().setHousehold(householdMapper.toDTO(household)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTransactions(TransactionsRequest request, StreamObserver<TransactionResponse> responseObserver) {
        Long householdId = request.getHouseholdId();

        Household household = householdRepository.getById(householdId);

        if (household == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Household with id '%d' not found", householdId))
                            .asRuntimeException()
            );
            return;
        }

        household.getTransactions()
                .forEach(t -> responseObserver.onNext(
                        TransactionResponse.newBuilder()
                                .setTransaction(transactionMapper.toDTO(t))
                                .build()
                ));
        responseObserver.onCompleted();
    }

    @Override
    public void getTransaction(GetOrDeleteTransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        Long transcationId = request.getTransactionId();

        Transaction transaction = transactionRepository.getById(transcationId);

        if (transaction == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Transaction with id '%d' not found", transcationId))
                            .asRuntimeException()
            );
            return;
        }

        responseObserver.onNext(
                TransactionResponse.newBuilder()
                        .setTransaction(transactionMapper.toDTO(transaction))
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void editTransaction(CreateOrEditTransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        if (!request.getTransaction().hasField(request.getTransaction().getDescriptorForType().findFieldByName("id"))) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Transaction id must be provided")
                            .asRuntimeException()
            );
            return;
        }

        Transaction transaction = transactionMapper.toEntity(request.getTransaction());

        if (!transactionRepository.findById(transaction.getId()).isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Transaction with id '%d' not found", transaction.getId()))
                            .asRuntimeException()
            );
            return;
        }

        transaction = transactionRepository.save(transaction);

        responseObserver.onNext(
                TransactionResponse.newBuilder()
                        .setTransaction(transactionMapper.toDTO(transaction))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void createTransaction(CreateOrEditTransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        Transaction transaction = transactionMapper.toEntity(request.getTransaction());
        transaction.setId(null);

        transaction = transactionRepository.save(transaction);

        responseObserver.onNext(
                TransactionResponse.newBuilder()
                        .setTransaction(transactionMapper.toDTO(transaction))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTransaction(GetOrDeleteTransactionRequest request, StreamObserver<SuccessResponse> responseObserver) {
        if (!request.hasField(request.getDescriptorForType().findFieldByName("id"))) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Transaction id must be provided")
                            .asRuntimeException()
            );
            return;
        }

        Optional<Transaction> transaction = transactionRepository.findById(request.getTransactionId());

        if (!transaction.isPresent()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Transaction with id '%d' not found", request.getTransactionId()))
                            .asRuntimeException()
            );
        } else {
            transactionRepository.delete(transaction.get());
        }

        responseObserver.onNext(SuccessResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void createHousehold(HouseholdRequest request, StreamObserver<SuccessResponse> responseObserver) {
        if (householdRepository.getByOwner(request.getOwner()) != null) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(String.format("Household for owner '%s' already exists", request.getOwner()))
                            .asRuntimeException()
            );
            return;
        }

        Household household = new Household();

        household.setOwner(request.getOwner());
        household.setBalance(0.0);
        household.setId(null);
        household.setTransactions(null);

        try {
            householdRepository.save(household);
            responseObserver.onNext(SuccessResponse.newBuilder().setSuccess(true).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void editHousehold(EditHouseholdRequest request, StreamObserver<SuccessResponse> responseObserver) {
        Household household = householdRepository.getById(request.getId());

        if (household == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Household with id '%d' not found", request.getId()))
                            .asRuntimeException()
            );
            return;
        }

        household.setBalance(request.getBalance());

        try {
            householdRepository.save(household);
            responseObserver.onNext(SuccessResponse.newBuilder().setSuccess(true).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
