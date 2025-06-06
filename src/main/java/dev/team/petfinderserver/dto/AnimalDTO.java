package dev.team.petfinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private Long id;
    private Boolean deliveredStatus;
    private String location;
    private Date date;
    private String description;
    private String photo;
    private AnimalCharacteristicsDTO characteristics;
} 