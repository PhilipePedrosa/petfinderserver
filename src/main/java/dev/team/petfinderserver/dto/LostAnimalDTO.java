package dev.team.petfinderserver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LostAnimalDTO {
    private Long id;
    private String name;
    private String location;
    private Date date;
    private boolean deliveredStatus;
    private String description;
    private String photo;
    private AnimalCharacteristicsDTO characteristics;
    private UserResponseDTO owner;
} 