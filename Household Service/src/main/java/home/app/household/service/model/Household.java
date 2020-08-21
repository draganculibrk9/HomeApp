package home.app.household.service.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public Household() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
