package dev.team.petfinderserver.repository;

import dev.team.petfinderserver.model.LostAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostAnimalRepository extends JpaRepository<LostAnimal, Long> {
} 