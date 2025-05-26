package com.example.user_service.services;

import com.example.user_service.models.dtos.RegisterDTO;
import com.example.user_service.models.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    @DisplayName("registrar nuevo usuario")
    public void registerTest() throws Exception {

        // test name no puede ser null
        RegisterDTO registerDTO1 = RegisterDTO.builder()
                .name(null)// nullable false
                .password("1234")
                .lastName("testing_last_name1")
                .email("testing_email1@test.com")
                .address("address1")
                .phone("test_111111")
                .build();

        assertThrows(Exception.class, () -> userService.register(registerDTO1));

        //test password no puede ser null
        RegisterDTO registerDTO2 = RegisterDTO.builder()
                .name("testing_name")// nullable false
                .password(null)// nullable false
                .lastName("testing_last_name2")// nullable false
                .email("testing_email2@test.com")// nullable false, unique= true
                .address("address2")
                .phone("test_222222")
                .build();

        assertThrows(Exception.class, () -> userService.register(registerDTO2));

        //test lastName no puede ser null
        RegisterDTO registerDTO3 = RegisterDTO.builder()
                .name("testing_name")// nullable false
                .password("1234")// nullable false
                .lastName(null)// nullable false
                .email("testing_email3@test.com")// nullable false, unique= true
                .address("address3")
                .phone("test_333333")
                .build();

        assertThrows(Exception.class, () -> userService.register(registerDTO3));

        //test email no puede ser null
        RegisterDTO registerDTO4 = RegisterDTO.builder()
                .name("testing_name4")// nullable false
                .password("1234")// nullable false
                .lastName("last_name4")// nullable false
                .email(null)// nullable false, unique= true
                .address("address4")
                .phone("test_4444444")
                .build();

        assertThrows(Exception.class, () -> userService.register(registerDTO4));

        //test email debe ser unico
        RegisterDTO registerDTO5 = RegisterDTO.builder()
                .name("testing_name5")// nullable false
                .password("1234")// nullable false
                .lastName("last_name5")// nullable false
                .email("test_email@test.com")// nullable false, unique= true
                .address("address5")
                .phone("test_5555555")
                .build();

        userService.register(registerDTO5);

        RegisterDTO registerDTO6 = RegisterDTO.builder()
                .name("testing_name6")// nullable false
                .password("1234")// nullable false
                .lastName("last_name6")// nullable false
                .email("test_email@test.com")// nullable false, unique= true
                .address("address6")
                .phone("test_66666")
                .build();

        assertThrows(Exception.class, () -> userService.register(registerDTO6));
    }

    @Test
    @DisplayName("obtener usuario por id")
    public void getUserByIdTest() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("testing_name7")// nullable false
                .password("1234")// nullable false
                .lastName("last_name7")// nullable false
                .email("test_email7@test.com")// nullable false, unique= true
                .address("address7")
                .phone("test_777777")
                .build();

        User user = userService.register(registerDTO);

        User userFound = userService.getUserById(user.getId());

        assertNotNull(userFound);
        assertEquals("testing_name7", userFound.getName());
        assertEquals("1234", userFound.getPassword());
        assertEquals("last_name7", userFound.getLastName());
        assertEquals("test_email7@test.com", userFound.getEmail());
        assertEquals("address7", userFound.getAddress());
        assertEquals("test_777777", userFound.getPhone());
    }
}
