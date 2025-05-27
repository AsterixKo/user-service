package com.example.user_service.controllers;

import com.example.user_service.models.entities.User;
import com.example.user_service.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class UserControllerMockedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("obtener todos los usuarios")
    public void getAllUsersTest() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .name("testing_controller_allusers1")
                .lastName("testing_controller_allusers1")
                .password("1234")
                .email("testing_controller_allusers1_email@test.com")
                .phone("111111111")
                .address("address_1")
                .build());
        users.add(User.builder()
                .name("testing_controller_allusers2")
                .lastName("testing_controller_allusers2")
                .password("1234")
                .email("testing_controller_allusers2_email@test.com")
                .phone("222222222")
                .address("address_2")
                .build());

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/user/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("testing_controller_allusers1"))
                .andExpect(jsonPath("$[1].name").value("testing_controller_allusers2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(users)));

        verify(userService).getAllUsers();
    }
}
