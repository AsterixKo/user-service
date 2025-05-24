package com.example.user_service.services;

import com.example.user_service.exceptions.UserNotFoundException;
import com.example.user_service.models.dtos.RegisterDTO;
import com.example.user_service.models.entities.User;
import com.example.user_service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("Usuario no encontrado");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User register(RegisterDTO registerDTO) throws Exception {
        try {
            User user = createUserFromRegistration(registerDTO);

            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error: {} haciendo register de registerDTO: {}", e.getMessage(), registerDTO);
            throw new Exception(e);
        }
    }

    private User createUserFromRegistration(RegisterDTO register) {
        User user = new User();
        user.setName(register.getName());
        user.setLastName(register.getLastName());
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        user.setAddress(register.getAddress());
        user.setPhone(register.getPhone());

        return user;
    }
}
