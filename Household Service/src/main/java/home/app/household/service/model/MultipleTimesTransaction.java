package home.app.household.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MultipleTimesTransaction extends Transaction {
    @Column(nullable = false)
    private Integer numberOfTimes;

    public Integer getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(Integer numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }
}
