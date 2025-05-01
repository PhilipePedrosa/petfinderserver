package dev.team.petfinderserver.repository;

import dev.team.petfinderserver.model.User;
import dev.team.petfinderserver.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUser(User user);
    
    @Query("SELECT ud FROM UserData ud WHERE ud.user.id = :userId")
    Optional<UserData> findByUserId(Long userId);
}
