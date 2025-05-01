package dev.team.petfinderserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_data")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_data_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_data_first_name", nullable = false)
    private String firstName;

    @Column(name = "user_data_last_name", nullable = false)
    private String lastName;

    @Column(name = "user_data_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_data_security_id", nullable = false, unique = true)
    private String securityId;

    @Column(name = "user_data_phoneNumber", nullable = false, unique = true)
    private String phoneNumber;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
