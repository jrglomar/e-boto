package com.jrtg.eboto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.User;
import com.jrtg.eboto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    User user1;
    User user2;
    User user3;
    List<User> userList;

    @BeforeEach
    void setup() {
        user1 = User.builder().userId(1L).firstName("jglomar").build();

        user2 = User.builder().userId(2L).firstName("agutierrez").build();

        user3 = User.builder().userId(3L).firstName("cbaqui").build();

        userList = List.of(user1, user2, user3);
    }


    @Test
    @DisplayName("Finding all user data")
    void findAll() throws Exception {

        when(userService.findAllUser()).thenReturn(userList);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("jglomar", "agutierrez", "cbaqui")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Finding user by id")
    void findById() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user1);

        mockMvc.perform(get("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Saving a new user")
    void save() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(jsonPath("$.firstName").value("jglomar"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Updating an existing user")
    void update() throws Exception {
        when(userService.updateUser(any(User.class), anyLong())).thenReturn(user1);

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Delete a user")
    void delete() throws Exception {
        when(userService.deleteUser(anyLong())).thenReturn("Record is deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"Record is deleted.\"}"))
                .andExpect(content().string("Record is deleted."))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws Exception {
        when(userService.findUserById(any()))
                .thenThrow(new RecordNotFoundException("Record not found."));

        mockMvc.perform(get("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("Record not found.", result.getResolvedException().getMessage()))
                .andExpect(status().isNotFound());
    }
}