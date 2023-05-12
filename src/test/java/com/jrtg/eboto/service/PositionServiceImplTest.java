package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;
import com.jrtg.eboto.model.Position;
import com.jrtg.eboto.repository.ElectionRepository;
import com.jrtg.eboto.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositionServiceImplTest {
    @Mock
    PositionRepository positionRepository;
    @Spy
    ElectionRepository electionRepository;
    @InjectMocks
    PositionService positionService = new PositionServiceImpl();

    Position position1;
    Position position2;
    Position position3;

    Election election1;

    List<Position> positionList;

    @BeforeEach
    void setup() {
        position1 = Position.builder().positionId(1L).title("").build();
        position2 = Position.builder().positionId(2L).title("").build();
        position3 = Position.builder().positionId(3L).title("").build();
        positionList = List.of(position1, position2, position3);
        election1 = Election.builder().electionId(1L).status(false).description("").title("").dateEnd("").build();
    }

    @Test
    @DisplayName("Finding position by id")
    void findPositionById() throws RecordNotFoundException {
        when(positionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(position1));

        Position result = positionService.findPositionById(1L);

        verify(positionRepository).findById(1L);
        assertThat(result).isEqualTo(position1);
    }

    @Test
    @DisplayName("Finding all position")
    void findAllPosition() throws RecordNotFoundException {
        when(positionRepository.findAll()).thenReturn(positionList);
        List<Position> resultList = positionService.findAllPosition();

        verify(positionRepository).findAll();
        assertThat(resultList).isEqualTo(positionList);

    }

    @Test
    @DisplayName("Creating a position")
    void savePosition() throws RecordNotFoundException {
        when(electionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(election1));
        when(positionRepository.save(any(Position.class))).thenReturn(position1);

        Position result = positionService.savePosition(position1, 1L);

        verify(electionRepository).findById(1L);
        verify(positionRepository).save(position1);

        assertThat(result).isEqualTo(position1);
        assertThat(result.getElection()).isEqualTo(election1);

    }

    @Test
    @DisplayName("Updating a position")
    void updatePosition() throws RecordNotFoundException {
        when(positionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(position1));

        Position updateSample = Position.builder().positionId(1L).title("updat3d").build();
        when(positionRepository.save(any(Position.class))).thenReturn(updateSample);

        Position result = positionService.updatePosition(updateSample, 1L);

        verify(positionRepository).save(updateSample);
        verify(positionRepository).findById(1L);
        assertThat(result).isEqualTo(position1);
    }

    @Test
    @DisplayName("Delete a position")
    void deletePosition() throws RecordNotFoundException {
        when(positionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(position1));

        Position deleteSample = Position.builder().positionId(1L).title("delet3d").build();

        when(positionRepository.save(any(Position.class))).thenReturn(deleteSample);

        String result = positionService.deletePosition(1L);

        verify(positionRepository).findById(1L);
        verify(positionRepository).save(deleteSample);
        assertThat(result).isEqualTo("Record deleted.");
    }

}
