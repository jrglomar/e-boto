package com.jrtg.eboto.controller;

import com.jrtg.eboto.service.VotingLineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest
public class VotingLineControllerTest {
    
    @MockBean
    VotingLineService votingLineService;

    @Test
    @DisplayName("Finding all votingline data")
    void findAll() {
    }

    @Test
    @DisplayName("Finding voting line by id")
    void findById() {
    }

    @Test
    @DisplayName("Saving a new votingline data")
    void save() {
    }

    @Test
    @DisplayName("Updating an existing votingline")
    void update() {
    }

    @Test
    @DisplayName("Deleting a votingline")
    void delete() {
    }
}