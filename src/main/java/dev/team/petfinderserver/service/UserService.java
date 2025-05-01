package dev.team.petfinderserver.service;

import dev.team.petfinderserver.dto.UserRequestDTO;
import dev.team.petfinderserver.dto.UserDataRequestDTO;
import dev.team.petfinderserver.model.User;
import dev.team.petfinderserver.model.UserData;
import dev.team.petfinderserver.repository.UserRepository;
import dev.team.petfinderserver.repository.UserDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserDataRepository userDataRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void register(UserRequestDTO userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        UserDataRequestDTO userDataRequest = userRequest.getUserData();
        UserData userData = new UserData();
        userData.setFirstName(userDataRequest.getFirstName());
        userData.setLastName(userDataRequest.getLastName());
        userData.setSecurityId(userDataRequest.getSecurityId());
        userData.setEmail(userDataRequest.getEmail());
        userData.setPhoneNumber(userDataRequest.getPhoneNumber());
        userData.setUser(user);
        user.setUserData(userData);

        save(user);
    }
}
