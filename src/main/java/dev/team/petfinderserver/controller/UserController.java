package dev.team.petfinderserver.controller;

import dev.team.petfinderserver.dto.UserRequestDTO;
import dev.team.petfinderserver.dto.UserResponseDTO;
import dev.team.petfinderserver.model.User;
import dev.team.petfinderserver.service.UserService;
import dev.team.petfinderserver.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
            );

            String token = jwtService.generateToken(authentication);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequest) {
        try {
            if (userService.findByUsername(userRequest.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Nome de usuário já existe");
            }

            User user = userService.register(userRequest);
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return userService.findById(id)
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    if (user.getUserData() != null) {
                        dto.setFirstName(user.getUserData().getFirstName());
                        dto.setLastName(user.getUserData().getLastName());
                        dto.setEmail(user.getUserData().getEmail());
                        dto.setSecurityId(user.getUserData().getSecurityId());
                        dto.setPhoneNumber(user.getUserData().getPhoneNumber());
                    }
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar usuário: " + e.getMessage());
        }
    }
}
