package home.app.services.service.model;

import home.app.grpc.api.model.Address;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String website;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;
}
