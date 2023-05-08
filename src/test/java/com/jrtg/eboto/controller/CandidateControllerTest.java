package com.jrtg.eboto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.service.CandidateService;
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

@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @MockBean
    private CandidateService candidateService;

    @Autowired
    private MockMvc mockMvc;

    Candidate candidate1;
    Candidate candidate2;
    Candidate candidate3;
    List<Candidate> candidateList;

    @BeforeEach
    void setup() {
        candidate1 = Candidate.builder().candidateId(1L).name("Raven").description("").build();

        candidate2 = Candidate.builder().candidateId(2L).name("AJ").description("").build();

        candidate3 = Candidate.builder().candidateId(3L).name("Baqui").description("").build();

        candidateList = List.of(candidate1, candidate2, candidate3);
    }


    @Test
    @DisplayName("Finding all candidate data")
    void findAll() throws Exception {

        when(candidateService.findAllCandidate()).thenReturn(candidateList);

        mockMvc.perform(get("/candidates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].name,description", containsInAnyOrder("AJ", "Raven", "Baqui")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Finding candidate by id")
    void findById() throws Exception {
        when(candidateService.findCandidateById(anyLong())).thenReturn(candidate1);

        mockMvc.perform(get("/candidates/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.candidateId").value(1))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("Saving a new candidate")
//    void save() throws Exception {
//        when(candidateService.saveCandidate(any(Candidate.class))).thenReturn(candidate1);
//
//        mockMvc.perform(post("/candidates")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(candidate1)))
//                //.andExpect(jsonPath("$.candidateName").value("Raven"))
//                //.andExpect(jsonPath("$.candidateId").value(1))
//                .andExpect(jsonPath("$.name").value("Raven"))
//                .andExpect(jsonPath("$.candidateId").value(1))
//                .andExpect(jsonPath("$.description").value(""))
//
//                .andExpect(status().isOk());
//    }

    @Test
    @DisplayName("Updating an existing candidate")
    void update() throws Exception {
        when(candidateService.updateCandidate(any(Candidate.class), anyLong())).thenReturn(candidate1);

        mockMvc.perform(put("/candidates/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(candidate1)))
                .andExpect(jsonPath("$.candidateId").value("1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Delete a candidate")
    void delete() throws Exception {
        when(candidateService.deleteCandidate(anyLong())).thenReturn("Record is deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/candidates/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"Record is deleted.\"}"))
                .andExpect(content().string("Record is deleted."))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws Exception {
        when(candidateService.findCandidateById(any()))
                .thenThrow(new RecordNotFoundException("Record not found."));

        mockMvc.perform(get("/candidates/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("Record not found.", result.getResolvedException().getMessage()))
                .andExpect(status().isNotFound());
    }
}