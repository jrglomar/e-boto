package com.jrtg.eboto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.VotingLine;
import com.jrtg.eboto.service.VotingLineService;
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

@WebMvcTest(VotingLineController.class)
public class VotingLineControllerTest {
    
    @MockBean
    VotingLineService votingLineService;
    
    @Autowired
    private MockMvc mockMvc;

    VotingLine votingLine1;
    VotingLine votingLine2;
    VotingLine votingLine3;
    Iterable<VotingLine> votingLineList;

    @BeforeEach
    void setup() {
        votingLine1 = VotingLine.builder().votingLineId(1L).votingLineTitle("SSC").build();

        votingLine2 = VotingLine.builder().votingLineId(2L).votingLineTitle("ESL").build();

        votingLine3 = VotingLine.builder().votingLineId(3L).votingLineTitle("QWE").build();

        votingLineList = List.of(votingLine1, votingLine2, votingLine3);
    }

    @Test
    @DisplayName("Finding all votingline data")
    void findAll() throws Exception {
        
        when(votingLineService.findAllVotingLine()).thenReturn(votingLineList);
        
        mockMvc.perform(get("/votinglines")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].votingLineTitle", containsInAnyOrder("SSC", "ESL", "QWE")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Finding voting line by id")
    void findById() throws Exception {
        when(votingLineService.findVotingLineById(anyLong())).thenReturn(votingLine1);

        mockMvc.perform(get("/votinglines/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.votingLineId").value(1))
                .andExpect(status().isOk());
        
    }

    @Test
    @DisplayName("Saving a new votingline data")
    void save() throws Exception {
        when(votingLineService.saveVotingLine(any(VotingLine.class))).thenReturn(votingLine1);
        
        mockMvc.perform(post("/votinglines")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(votingLine1)))
                .andExpect(jsonPath("$.votingLineTitle").value("SSC"))
                .andExpect(jsonPath("$.votingLineId").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Updating an existing votingline")
    void update() throws Exception {
        when(votingLineService.updateVotingLine(any(VotingLine.class), anyLong())).thenReturn(votingLine1);
        
        mockMvc.perform(put("/votinglines/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(votingLine1)))
                .andExpect(jsonPath("$.votingLineId").value(1))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Deleting a votingline")
    void delete() throws Exception {
        when(votingLineService.deleteVotingLine(anyLong())).thenReturn("Record is deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/votinglines/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"Record is deleted.\"}"))
                .andExpect(content().string("Record is deleted."))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws Exception {
        when(votingLineService.findVotingLineById(any()))
                .thenThrow(new RecordNotFoundException("Record not found."));

        mockMvc.perform(get("/votinglines/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("Record not found.", result.getResolvedException().getMessage()))
                .andExpect(status().isNotFound());
    }
}