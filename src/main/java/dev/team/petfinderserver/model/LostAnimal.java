package dev.team.petfinderserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lost_animal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LostAnimal extends Animal {
    @Column(name = "lost_animal_name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id")
    private User owner;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_characteristics_id_fk", nullable = false)
    private AnimalCharacteristics characteristics;
}