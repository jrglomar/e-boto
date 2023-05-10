package com.jrtg.eboto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;
import com.jrtg.eboto.service.ElectionService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ElectionController.class)
public class ElectionControllerTest {

    @MockBean
    private ElectionService electionService;

    @Autowired
    private MockMvc mockMvc;

    Election election1;
    Election election2;
    Election election3;
    List<Election> electionList;

    @BeforeEach
    void setup() {
        election1 = Election.builder().electionId(1L).title("Raven").build();
        election2 = Election.builder().electionId(2L).title("AJ").build();
        //election2 = Election.builder().electionId(2L).electionName("AJ").build();

        election3 = Election.builder().electionId(3L).title("Baqui").build();

        electionList = List.of(election1, election2, election3);
    }


    @Test
    @DisplayName("Finding all election data")
    void findAll() throws Exception {

        when(electionService.findAllElection()).thenReturn(electionList);

        mockMvc.perform(get("/elections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("AJ", "Raven", "Baqui")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Finding election by id")
    void findById() throws Exception {
        when(electionService.findElectionById(anyLong())).thenReturn(election1);

        mockMvc.perform(get("/elections/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.electionId").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Saving a new election")
    void save() throws Exception {
        when(electionService.saveElection(any(Election.class))).thenReturn(election1);

        mockMvc.perform(post("/elections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(election1)))
                .andExpect(jsonPath("$.title").value("Raven"))
                .andExpect(jsonPath("$.electionId").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Updating an existing election")
    void update() throws Exception {
        when(electionService.updateElection(any(Election.class), anyLong())).thenReturn(election1);

        mockMvc.perform(put("/elections/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(election1)))
                .andExpect(jsonPath("$.electionId").value("1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Delete a election")
    void delete() throws Exception {
        when(electionService.deleteElection(anyLong())).thenReturn("Record is deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/elections/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"Record is deleted.\"}"))
                .andExpect(content().string("Record is deleted."))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws Exception {
        when(electionService.findElectionById(any()))
                .thenThrow(new RecordNotFoundException("Record not found."));

        mockMvc.perform(get("/elections/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("Record not found.", result.getResolvedException().getMessage()))
                .andExpect(status().isNotFound());
    }
}