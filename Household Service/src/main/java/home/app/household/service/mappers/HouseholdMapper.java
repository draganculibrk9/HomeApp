package home.app.household.service.mappers;

import home.app.grpc.HouseholdMessage;
import home.app.grpc.api.model.IMapper;
import home.app.household.service.model.Household;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class HouseholdMapper implements IMapper<Household, HouseholdMessage> {
    @Override
    public Household toEntity(HouseholdMessage dto) {
        Household household = new Household();

        household.setBalance(dto.getBalance());
        household.setId(dto.getId());
        household.setOwner(dto.getOwner());
        household.setTransactions(new HashSet<>());

        return household;
    }

    @Override
    public HouseholdMessage toDTO(Household household) {
        return HouseholdMessage.newBuilder()
                .setBalance(household.getBalance())
                .setId(household.getId())
                .setOwner(household.getOwner())
                .build();
    }
}
