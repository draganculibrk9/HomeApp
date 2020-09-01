package home.app.services.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String website;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;
}
