package dev.team.petfinderserver.service;

import dev.team.petfinderserver.model.AnimalCharacteristics;
import dev.team.petfinderserver.model.FoundAnimal;
import dev.team.petfinderserver.model.LostAnimal;
import dev.team.petfinderserver.repository.AnimalCharacteristicsRepository;
import dev.team.petfinderserver.repository.FoundAnimalRepository;
import dev.team.petfinderserver.repository.LostAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final LostAnimalRepository lostAnimalRepository;
    private final FoundAnimalRepository foundAnimalRepository;
    private final AnimalCharacteristicsRepository characteristicsRepository;

    @Transactional
    public LostAnimal saveLostAnimal(LostAnimal animal) {
        AnimalCharacteristics savedCharacteristics = characteristicsRepository.save(animal.getCharacteristics());
        animal.setCharacteristics(savedCharacteristics);

        return lostAnimalRepository.save(animal);
    }

    @Transactional
    public FoundAnimal saveFoundAnimal(FoundAnimal animal) {
        AnimalCharacteristics savedCharacteristics = characteristicsRepository.save(animal.getCharacteristics());
        animal.setCharacteristics(savedCharacteristics);

        return foundAnimalRepository.save(animal);
    }

    public List<LostAnimal> findAllLostAnimals() {
        return lostAnimalRepository.findAll();
    }

    public Optional<LostAnimal> findLostAnimalById(Long id) {
        return lostAnimalRepository.findById(id);
    }

    public List<FoundAnimal> findAllFoundAnimals() {
        return foundAnimalRepository.findAll();
    }

    public Optional<FoundAnimal> findFoundAnimalById(Long id) {
        return foundAnimalRepository.findById(id);
    }
}