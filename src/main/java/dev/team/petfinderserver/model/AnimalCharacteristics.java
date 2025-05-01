package dev.team.petfinderserver.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animal_characteristics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnimalCharacteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_characteristics_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "animal_characteristics_species", nullable = false)
    private String species;

    @Column(name = "animal_characteristics_race", nullable = false)
    private String breed;

    @Column(name = "animal_characteristics_color", nullable = false)
    private String color;

    @Column(name = "animal_characteristics_size", nullable = false)
    private String size;
}
