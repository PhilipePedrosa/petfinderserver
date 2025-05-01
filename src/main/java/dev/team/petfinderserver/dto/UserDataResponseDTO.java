package dev.team.petfinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String securityId;
    private String email;
    private String phoneNumber;
} 