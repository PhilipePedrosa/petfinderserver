package dev.team.petfinderserver.repository;

import dev.team.petfinderserver.model.FoundAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundAnimalRepository extends JpaRepository<FoundAnimal, Long> {
} 