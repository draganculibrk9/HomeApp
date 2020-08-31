package home.app.services.service.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AccommodationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long household;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIME)
    private Date filedOn;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIME)
    private Date requestedFor;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    private Integer rating;

    @OneToOne(fetch = FetchType.EAGER)
    private Accommodation accommodation;

    public AccommodationRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHousehold() {
        return household;
    }

    public void setHousehold(Long household) {
        this.household = household;
    }

    public Date getFiledOn() {
        return filedOn;
    }

    public void setFiledOn(Date filedOn) {
        this.filedOn = filedOn;
    }

    public Date getRequestedFor() {
        return requestedFor;
    }

    public void setRequestedFor(Date requestedFor) {
        this.requestedFor = requestedFor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
