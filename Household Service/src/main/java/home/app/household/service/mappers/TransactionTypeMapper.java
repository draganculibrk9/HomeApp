package home.app.household.service.mappers;

import home.app.grpc.TransactionMessage;
import home.app.household.service.model.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeMapper implements IMapper<TransactionType, TransactionMessage.TransactionType> {
    @Override
    public TransactionType toEntity(TransactionMessage.TransactionType dto) {
        if (dto == TransactionMessage.TransactionType.INCOME) {
            return TransactionType.INCOME;
        }
        return TransactionType.EXPENDITURE;
    }

    @Override
    public TransactionMessage.TransactionType toDTO(TransactionType transactionType) {
        if (transactionType == TransactionType.INCOME) {
            return TransactionMessage.TransactionType.INCOME;
        }
        return TransactionMessage.TransactionType.EXPENDITURE;
    }
}
