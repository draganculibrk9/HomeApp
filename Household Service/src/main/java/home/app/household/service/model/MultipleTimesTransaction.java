package home.app.household.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class MultipleTimesTransaction extends Transaction {
    @Column
    private Long numberOfTimes;
}
