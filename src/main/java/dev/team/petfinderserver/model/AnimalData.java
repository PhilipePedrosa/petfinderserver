package dev.team.petfinderserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "animal_data")
@Setter
@Getter
public class AnimalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
