package home.app.services.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccommodationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long household;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date filedOn;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date requestedFor;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToOne(fetch = FetchType.EAGER)
    private Accommodation accommodation;
}
