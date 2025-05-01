package dev.team.petfinderserver.service;

import dev.team.petfinderserver.auth.CustomUserDetails;
import dev.team.petfinderserver.model.User;
import dev.team.petfinderserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Tentando carregar usuário: {}", username);
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado: {}", username);
                    return new UsernameNotFoundException("User not found");
                });
        log.info("Usuário encontrado: {}", username);
        return new CustomUserDetails(user);
    }
}