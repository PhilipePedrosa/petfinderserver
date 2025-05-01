package dev.team.petfinderserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.team.petfinderserver.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
