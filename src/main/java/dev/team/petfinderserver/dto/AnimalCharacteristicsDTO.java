package dev.team.petfinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCharacteristicsDTO {
    private Long id;
    private String species;
    private String breed;
    private String color;
    private String size;

} 