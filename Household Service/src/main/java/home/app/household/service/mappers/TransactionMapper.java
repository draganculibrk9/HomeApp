package home.app.household.service.mappers;

import home.app.grpc.TransactionMessage;
import home.app.household.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionMapper implements IMapper<Transaction, TransactionMessage> {
    @Autowired
    private PeriodMapper periodMapper;

    @Autowired
    private TransactionTypeMapper transactionTypeMapper;

    @Override
    public Transaction toEntity(TransactionMessage dto) {
        Transaction transaction;
        if (dto.hasField(dto.getDescriptorForType().findFieldByName("number_of_times"))) {
            transaction = new MultipleTimesTransaction();

            ((MultipleTimesTransaction) transaction).setNumberOfTimes(dto.getNumberOfTimes());
        } else if (dto.hasField(dto.getDescriptorForType().findFieldByName("period"))) {
            transaction = new PeriodicalTransaction();

            ((PeriodicalTransaction) transaction).setPeriod(periodMapper.toEntity(dto.getPeriod()));
        } else {
            transaction = new Transaction();
        }
        setCommonFields(transaction, dto);

        return transaction;
    }

    @Override
    public TransactionMessage toDTO(Transaction transaction) {
        TransactionMessage.Builder message = TransactionMessage.newBuilder()
                .setAmount(transaction.getAmount())
                .setDate(transaction.getDate().getTime())
                .setId(transaction.getId())
                .setName(transaction.getName())
                .setTransactionType(transactionTypeMapper.toDTO(transaction.getType()));

        if (transaction instanceof PeriodicalTransaction) {
            message.setPeriod(periodMapper.toDTO(((PeriodicalTransaction) transaction).getPeriod()));
        } else if (transaction instanceof MultipleTimesTransaction) {
            message.setNumberOfTimes(((MultipleTimesTransaction) transaction).getNumberOfTimes());
        }

        return message.build();
    }


    private <T extends Transaction> void setCommonFields(T transaction, TransactionMessage dto) {
        transaction.setId(dto.getId());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(new Date(dto.getDate() * 1000));
        transaction.setName(dto.getName());
        transaction.setType(transactionTypeMapper.toEntity(dto.getTransactionType()));
    }
}
