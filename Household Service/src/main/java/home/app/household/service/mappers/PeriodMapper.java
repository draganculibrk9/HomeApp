package home.app.household.service.mappers;

import home.app.household.service.model.Period;
import org.springframework.stereotype.Component;

@Component
public class PeriodMapper implements IMapper<Period, home.app.grpc.Period> {
    @Override
    public Period toEntity(home.app.grpc.Period dto) {
        switch (dto) {
            case WEEK:
                return Period.WEEK;
            case MONTH:
                return Period.MONTH;
            default:
                return Period.YEAR;
        }
    }

    @Override
    public home.app.grpc.Period toDTO(Period period) {
        switch (period) {
            case WEEK:
                return home.app.grpc.Period.WEEK;
            case MONTH:
                return home.app.grpc.Period.MONTH;
            default:
                return home.app.grpc.Period.YEAR;
        }
    }
}
