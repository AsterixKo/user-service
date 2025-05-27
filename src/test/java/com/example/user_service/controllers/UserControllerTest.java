package com.example.user_service.controllers;

import com.example.user_service.models.dtos.RegisterDTO;
import com.example.user_service.models.entities.User;
import com.example.user_service.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    private UserService mockUserService = org.mockito.Mockito.mock(UserService.class);

    private MockMvc mockMvc; // esta clase nos servirá para simular peticiones HTTP

    private final ObjectMapper objectMapper = new ObjectMapper(); // es una herramienta para convertir objetos a JSON.


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("registrar un usuario")
    public void registerUser() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("testing_controller_name1")
                .lastName("testing_controller_lastName1")
                .password("1234")
                .email("testing_controller_email1@test.com")
                .phone("1111111111")
                .address("address_1")
                .build();
        String registerDTOJson = objectMapper.writeValueAsString(registerDTO);

        MvcResult result = mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerDTOJson))
                .andExpect(status().isOk()).andReturn();

        String stringResponse = result.getResponse().getContentAsString();
        assertTrue(stringResponse.contains("testing_controller_name1"));
    }

    @Test
    @DisplayName("obtener el usuario por id")
    public void getUserById() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("testing_controller_name2")
                .lastName("testing_controller_lastName2")
                .password("1234")
                .email("testing_controller_email2@test.com")
                .phone("22222222222")
                .address("address_2")
                .build();

        User userCreated = userService.register(registerDTO);

        MvcResult result = mockMvc.perform(get("/api/user/" + userCreated.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("testing_controller_email2@test.com"));
    }

}
