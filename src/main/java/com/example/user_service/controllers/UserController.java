package com.example.user_service.controllers;

import com.example.user_service.exceptions.UserNotFoundException;
import com.example.user_service.models.dtos.RegisterDTO;
import com.example.user_service.models.entities.User;
import com.example.user_service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> userList = userService.getAllUsers();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error solicitando todos los users {}", e.getMessage());
            return new ResponseEntity<>("Error solicitando todos los users", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("Error: {} Usuario no encontrado por id {}", e.getMessage(), id);
            return new ResponseEntity<>("Error usuario no encontrado", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error de servidor: {} Usuario no encontrado por id {}", e.getMessage(), id);
            return new ResponseEntity<>("Error de servidor, usuario no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            return new ResponseEntity<>(userService.register(registerDTO), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error: {} haciendo register", e.getMessage());
            return new ResponseEntity<>("Error haciendo register", HttpStatus.BAD_REQUEST);
        }
    }
}
