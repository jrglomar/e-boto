package com.jrtg.eboto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrtg.eboto.model.Position;
import com.jrtg.eboto.service.PositionService;
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

@WebMvcTest(PositionController.class)
public class PositionControllerTest {
    @MockBean
    private PositionService positionService;

    @Autowired
    private MockMvc mockMvc;

    Position position1;
    Position position2;
    Position position3;
    List<Position> positionList;

    @BeforeEach
    void setup() {
        position1 = Position.builder().positionId(1L).title("President").build();
        position2 = Position.builder().positionId(2L).title("Vice-President").build();
        position3 = Position.builder().positionId(3L).title("Mayor").build();
        positionList = List.of(position1, position2, position3);

    }
    @Test
    @DisplayName("Finding all position data")
    void findAll() throws Exception {
        when(positionService.findAllPosition()).thenReturn(positionList);
        mockMvc.perform(get("/position")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].title",containsInAnyOrder("President","Vice-President","Mayor")))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("Finding position by id")
    void findById()throws Exception{
        when(positionService.findPositionById(anyLong())).thenReturn(position2);

        mockMvc.perform(get("/position/{id}",2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.positionId").value(2))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Saving a new position")
    void save()throws Exception{
        when(positionService.savePosition(any(Position.class),anyLong())).thenReturn(position1);

        mockMvc.perform(post("/position/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(position1)))
                .andExpect(jsonPath("$.title").value("President"))
                .andExpect(jsonPath("$.positionId").value(1L))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Updating as existing position")
    void update()throws Exception{
        when(positionService.updatePosition(any(Position.class),anyLong())).thenReturn(position1);

        mockMvc.perform(put("/position/{id}",1) //request http
                .contentType(MediaType.APPLICATION_JSON)            //media type json file
                .content(new ObjectMapper().writeValueAsString(position1))) // to write a request api
                .andExpect(jsonPath("$.positionId").value("1")) //expected values
                .andExpect(status().isAccepted());                                       //test for the response value
    }
    @Test
    @DisplayName("Delete a position")
    void delete() throws Exception{
        when(positionService.deletePosition(anyLong())).thenReturn("Record is deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/position/{id}", 1) //request
                .contentType(MediaType.APPLICATION_JSON)                                        //media type which is json file
                        .content("{\"Record is deleted.\"}"))
                .andExpect(content().string("Record is deleted."))                 //expected value
                .andExpect(status().isAccepted());                                                 //test for the response value
    }
}
