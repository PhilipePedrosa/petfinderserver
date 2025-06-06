package dev.team.petfinderserver.controller;

import dev.team.petfinderserver.dto.*;
import dev.team.petfinderserver.model.*;
import dev.team.petfinderserver.service.AnimalService;
import dev.team.petfinderserver.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/lost")
    public ResponseEntity<LostAnimalDTO> reportLostAnimal(@RequestBody LostAnimalRequestDTO request, Authentication authentication) {
        LostAnimal lostAnimal = convertToLostAnimal(request);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        lostAnimal.setOwner(userDetails.getUser());
        LostAnimal savedAnimal = animalService.saveLostAnimal(lostAnimal);
        return ResponseEntity.ok(convertToLostAnimalDTO(savedAnimal));
    }

    @GetMapping("/lost")
    public ResponseEntity<List<LostAnimalDTO>> getLostAnimals() {
        List<LostAnimal> animals = animalService.findAllLostAnimals();
        List<LostAnimalDTO> dtos = animals.stream()
                .map(this::convertToLostAnimalDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/lost/{id}")
    public ResponseEntity<LostAnimalDTO> getLostAnimalById(@PathVariable Long id) {
        return animalService.findLostAnimalById(id)
                .map(this::convertToLostAnimalDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/found")
    public ResponseEntity<FoundAnimalDTO> reportFoundAnimal(@RequestBody FoundAnimalRequestDTO request, Authentication authentication) {
        FoundAnimal foundAnimal = convertToFoundAnimal(request);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        foundAnimal.setFinder(userDetails.getUser());
        FoundAnimal savedAnimal = animalService.saveFoundAnimal(foundAnimal);
        return ResponseEntity.ok(convertToFoundAnimalDTO(savedAnimal));
    }

    @GetMapping("/found")
    public ResponseEntity<List<FoundAnimalDTO>> getFoundAnimals() {
        List<FoundAnimal> animals = animalService.findAllFoundAnimals();
        List<FoundAnimalDTO> dtos = animals.stream()
                .map(this::convertToFoundAnimalDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/found/{id}")
    public ResponseEntity<FoundAnimalDTO> getFoundAnimalById(@PathVariable Long id) {
        return animalService.findFoundAnimalById(id)
                .map(this::convertToFoundAnimalDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private LostAnimal convertToLostAnimal(LostAnimalRequestDTO dto) {
        LostAnimal animal = new LostAnimal();
        animal.setName(dto.getName());
        animal.setLocation(dto.getLocation());
        animal.setDate(dto.getDate());
        animal.setDeliveredStatus(false);
        animal.setDescription(dto.getDescription());
        
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            animal.setPhoto(Base64.getDecoder().decode(dto.getPhoto()));
        }
        
        AnimalCharacteristics characteristics = new AnimalCharacteristics();
        characteristics.setSpecies(dto.getCharacteristics().getSpecies());
        characteristics.setBreed(dto.getCharacteristics().getBreed());
        characteristics.setColor(dto.getCharacteristics().getColor());
        characteristics.setSize(dto.getCharacteristics().getSize());
        animal.setCharacteristics(characteristics);
        
        return animal;
    }

    private FoundAnimal convertToFoundAnimal(FoundAnimalRequestDTO dto) {
        FoundAnimal animal = new FoundAnimal();
        animal.setName(dto.getName());
        animal.setLocation(dto.getLocation());
        animal.setDate(dto.getDate());
        animal.setDeliveredStatus(false);
        animal.setDescription(dto.getDescription());
        
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            animal.setPhoto(Base64.getDecoder().decode(dto.getPhoto()));
        }
        
        AnimalCharacteristics characteristics = new AnimalCharacteristics();
        characteristics.setSpecies(dto.getCharacteristics().getSpecies());
        characteristics.setBreed(dto.getCharacteristics().getBreed());
        characteristics.setColor(dto.getCharacteristics().getColor());
        characteristics.setSize(dto.getCharacteristics().getSize());
        animal.setCharacteristics(characteristics);
        
        return animal;
    }

    private LostAnimalDTO convertToLostAnimalDTO(LostAnimal animal) {
        LostAnimalDTO dto = new LostAnimalDTO();
        dto.setId(animal.getId());
        dto.setDeliveredStatus(animal.getDeliveredStatus());
        dto.setLocation(animal.getLocation());
        dto.setDate(animal.getDate());
        dto.setName(animal.getName());
        dto.setDescription(animal.getDescription());
        
        if (animal.getPhoto() != null) {
            dto.setPhoto(Base64.getEncoder().encodeToString(animal.getPhoto()));
        }
        
        AnimalCharacteristicsDTO characteristicsDTO = new AnimalCharacteristicsDTO();
        characteristicsDTO.setId(animal.getCharacteristics().getId());
        characteristicsDTO.setSpecies(animal.getCharacteristics().getSpecies());
        characteristicsDTO.setBreed(animal.getCharacteristics().getBreed());
        characteristicsDTO.setColor(animal.getCharacteristics().getColor());
        characteristicsDTO.setSize(animal.getCharacteristics().getSize());
        dto.setCharacteristics(characteristicsDTO);

        if (animal.getOwner() != null) {
            User owner = animal.getOwner();
            UserData userData = owner.getUserData();

            UserResponseDTO ownerDTO = new UserResponseDTO();
            ownerDTO.setId(owner.getId());
            ownerDTO.setUsername(owner.getUsername());

            if (userData != null) {
                ownerDTO.setFirstName(userData.getFirstName());
                ownerDTO.setLastName(userData.getLastName());
                ownerDTO.setSecurityId(userData.getSecurityId());
                ownerDTO.setEmail(userData.getEmail());
                ownerDTO.setPhoneNumber(userData.getPhoneNumber());
            }

            dto.setOwner(ownerDTO);
        }
        
        return dto;
    }

    private FoundAnimalDTO convertToFoundAnimalDTO(FoundAnimal animal) {
        FoundAnimalDTO dto = new FoundAnimalDTO();
        dto.setId(animal.getId());
        dto.setDeliveredStatus(animal.getDeliveredStatus());
        dto.setLocation(animal.getLocation());
        dto.setDate(animal.getDate());
        dto.setName(animal.getName());
        dto.setDescription(animal.getDescription());
        
        if (animal.getPhoto() != null) {
            dto.setPhoto(Base64.getEncoder().encodeToString(animal.getPhoto()));
        }
        
        AnimalCharacteristicsDTO characteristicsDTO = new AnimalCharacteristicsDTO();
        characteristicsDTO.setId(animal.getCharacteristics().getId());
        characteristicsDTO.setSpecies(animal.getCharacteristics().getSpecies());
        characteristicsDTO.setBreed(animal.getCharacteristics().getBreed());
        characteristicsDTO.setColor(animal.getCharacteristics().getColor());
        characteristicsDTO.setSize(animal.getCharacteristics().getSize());
        dto.setCharacteristics(characteristicsDTO);

        if (animal.getFinder() != null) {
            User finder = animal.getFinder();
            UserData userData = finder.getUserData();

            UserResponseDTO finderDTO = new UserResponseDTO();
            finderDTO.setId(finder.getId());
            finderDTO.setUsername(finder.getUsername());

            if (userData != null) {
                finderDTO.setFirstName(userData.getFirstName());
                finderDTO.setLastName(userData.getLastName());
                finderDTO.setSecurityId(userData.getSecurityId());
                finderDTO.setEmail(userData.getEmail());
                finderDTO.setPhoneNumber(userData.getPhoneNumber());
            }

            dto.setFinder(finderDTO);
        }
        
        return dto;
    }
}
