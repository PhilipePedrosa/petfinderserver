package dev.team.petfinderserver.repository;

import dev.team.petfinderserver.model.AnimalCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalCharacteristicsRepository extends JpaRepository<AnimalCharacteristics, Long> {

}
