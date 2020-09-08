package home.app.household.service.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class PeriodicalTransaction extends Transaction {
    @Column
    @Enumerated(EnumType.STRING)
    private Period period;
}
