package com.jrtg.eboto.service;

import com.jrtg.eboto.model.VotingLine;
import com.jrtg.eboto.repository.VotingLineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingLineServiceImplTest {

    @Mock
    VotingLineRepository votingLineRepository;

    @InjectMocks
    VotingLineService votingLineService = new VotingLineServiceImpl();

    VotingLine votingLine1;
    VotingLine votingLine2;
    VotingLine votingLine3;
    List<VotingLine> votingLinesList;


    @BeforeEach
    void setup() {
        votingLine1 = VotingLine.builder()
                .votingLineId(1L)
                .votingLineTitle("President Position")
                .build();
        votingLine2 = VotingLine.builder()
                .votingLineId(1L)
                .votingLineTitle("Vice President")
                .build();
        votingLine3 = VotingLine.builder()
                .votingLineId(1L)
                .votingLineTitle("Secretary")
                .build();

        votingLinesList = List.of(votingLine1, votingLine2, votingLine3);
    }


    @Test
    @DisplayName("Finding voting line by id")
    void findVotingLineById() throws RecordNotFoundException {
        when(votingLineRepository.findById(anyLong())).thenReturn(Optional.ofNullable(votingLine1));

        VotingLine result = votingLineService.findVotingLineById(1L);

        verify(votingLineRepository).findById(1L);
        assertThat(result).isEqualTo(votingLine1);
    }

    @Test
    @DisplayName("Finding all voting line")
    void findAllVotingLine() {
        when(votingLineRepository.findAll()).thenReturn(votingLinesList);

        List<VotingLine> result = votingLineService.findAllVotingLine();

        verify(votingLineRepository).findAll();
        assertThat(result).isEqualTo(votingLinesList);
    }

    @Test
    void saveVotingLine() {
        when(votingLineRepository.save(any(VotingLine.class))).thenReturn(votingLine1);

        VotingLine result = votingLineService.saveVotingLine(votingLine1);

        verify(votingLineRepository).save(votingLine1);
        assertThat(result).isEqualTo(votingLine1);
    }

    @Test
    void updateVotingLine() throws RecordNotFoundException {
        when(votingLineRepository.findById(anyLong())).thenReturn(Optional.ofNullable(votingLine1));

        VotingLine updateSample = new VotingLine(1L, "updat3d");
        when(votingLineRepository.save(any(VotingLine.class))).thenReturn(updateSample);
        VotingLine result = votingLineService.updateVotingLine(updateSample, 1L);

        verify(votingLineRepository).findById(1L);
        verify(votingLineRepository).save(updateSample);
        assertThat(result).isEqualTo(updateSample);
    }

    @Test
    void deleteVotingLine() throws RecordNotFoundException {
        when(votingLineRepository.findById(anyLong())).thenReturn(Optional.ofNullable(votingLine1));

        VotingLine deleteSample = new VotingLine(1L, "delet3d");
        when(votingLineRepository.save(any(VotingLine.class))).thenReturn(deleteSample);
        String result = votingLineService.deleteVotingLine(1L);

        verify(votingLineRepository).findById(1L);
        verify(votingLineRepository).save(deleteSample);
        assertThat(result).isEqualTo("Record deleted.");
    }


    @Test
    @DisplayName("Record not found")
    void findVotingLineByIdError() throws RecordNotFoundException {
        assertThrows(RecordNotFoundException.class, () -> {
            votingLineService.findVotingLineById(1L);
        }, "Record not found.");
    }
}