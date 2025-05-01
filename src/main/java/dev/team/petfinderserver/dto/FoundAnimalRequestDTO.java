package dev.team.petfinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoundAnimalRequestDTO {
    private String location;
    private Date date;
    private AnimalCharacteristicsDTO characteristics;
}
