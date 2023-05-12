package com.jrtg.eboto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrtg.eboto.model.CandidateVote;
import com.jrtg.eboto.service.CandidateVoteService;
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

@WebMvcTest(CandidateVoteController.class)
public class CandidateVoteControllerTest {

    @MockBean
    private CandidateVoteService candidateVoteService;
    @Autowired
    private MockMvc mockMvc;

    CandidateVote candidateVote1;
    CandidateVote candidateVote2;
    CandidateVote candidateVote3;
    List<CandidateVote> candidateVoteList;

    @BeforeEach
    void setup() {
        candidateVote1 = CandidateVote.builder().candidateVoteId(1L).voteReference("President").build();
        candidateVote2 = CandidateVote.builder().candidateVoteId(2L).voteReference("VicePresident").build();
        candidateVote3 = CandidateVote.builder().candidateVoteId(3L).voteReference("Senetors").build();

        candidateVoteList = List.of(candidateVote1, candidateVote2, candidateVote3);
    }

    @Test
    @DisplayName("Finding all candidate vote data")
    void findAll() throws Exception {

        when(candidateVoteService.findAllCandidateVote()).thenReturn(candidateVoteList);

        mockMvc.perform(get("/candidatevote")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].voteReference", containsInAnyOrder("President", "VicePresident", "Senetors")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Finding candidate by id")
    void findById() throws Exception {
        when(candidateVoteService.findCandidateVoteById(anyLong())).thenReturn(candidateVote1);

        mockMvc.perform(get("/candidatevote/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.candidateVoteId").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("saving a new candidate")
    void save() throws Exception {
        when(candidateVoteService.saveCandidateVote(any(CandidateVote.class), anyLong(), anyLong())).thenReturn(candidateVote1);
        mockMvc.perform(post("/candidatevote/{userId}/{candidateId}", 1,1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(candidateVote1)))
                .andExpect(jsonPath("$.candidateVoteId").value(1L))
                .andExpect(jsonPath("$.voteReference").value("President"))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("Updating an existing candidate")
    void update() throws Exception{
        when(candidateVoteService.updateCandidateVote(any(CandidateVote.class),anyLong())).thenReturn(candidateVote1);

        mockMvc.perform(put("/candidatevote/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(candidateVote1)))
                .andExpect(jsonPath("$.candidateVoteId").value("1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Delete a candidate")
    void delete()throws Exception{
        when(candidateVoteService.deleteCandidateVote(anyLong())).thenReturn("Record is deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/candidatevote/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"Record is deleted.\"}"))
                .andExpect(content().string("Record is deleted."))
                .andExpect(status().isAccepted());

    }
}
