package dev.team.petfinderserver.model;

import java.util.Date;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "animal_delivered_status", nullable = false)
    private Boolean deliveredStatus;

    @Column(name = "animal_location", nullable = false)
    private String location;

    @Column(name = "animal_date", nullable = false)
    private Date date;

    @Column(name = "animal_description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_characteristics_id_fk", nullable = false)
    private AnimalCharacteristics characteristics;
}
