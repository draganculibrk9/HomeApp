package home.app.services.service.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccommodationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
