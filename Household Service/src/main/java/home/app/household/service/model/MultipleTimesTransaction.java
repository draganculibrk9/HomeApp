package home.app.household.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MultipleTimesTransaction extends Transaction {
    @Column
    private Long numberOfTimes;

    public Long getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(Long numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }
}
