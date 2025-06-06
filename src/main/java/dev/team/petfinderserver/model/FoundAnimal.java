package dev.team.petfinderserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "found_animal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoundAnimal extends Animal {
    @Column(name = "found_animal_name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id")
    private User finder;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_characteristics_id_fk", nullable = false)
    private AnimalCharacteristics characteristics;
}