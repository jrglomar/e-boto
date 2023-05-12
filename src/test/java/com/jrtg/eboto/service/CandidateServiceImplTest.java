package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.model.Position;
import com.jrtg.eboto.repository.CandidateRepository;
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
class CandidateServiceImplTest {

    @Mock
    CandidateRepository candidateRepository;


    @Spy
    PositionRepository positionRepository;
    @InjectMocks
    CandidateService candidateService = new CandidateServiceImpl();

    Candidate candidate1;
    Candidate candidate2;
    Candidate candidate3;
    Position position1;

    List<Candidate> candidateList;


    @BeforeEach
    void setup() {
        candidate1 = Candidate.builder().candidateId(1L).name("Raven").build();

        candidate2 = Candidate.builder().candidateId(1L).name("AJ").build();

        candidate3 = Candidate.builder().candidateId(1L).name("Baqui").build();

        position1 = Position.builder().positionId(1L).title("").build();

        candidateList = List.of(candidate1, candidate2, candidate3);


    }

    @Test
    @DisplayName("Finding candidate by id")
    void findCandidateById() throws RecordNotFoundException {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidate1));

        Candidate result = candidateService.findCandidateById(1L);

        verify(candidateRepository).findById(1L);
        assertThat(result).isEqualTo(candidate1);
    }

    @Test
    @DisplayName("Finding all candidate")
    void findAllCandidate() {
        when(candidateRepository.findAll()).thenReturn(candidateList);

        List<Candidate> result = candidateService.findAllCandidate();

        verify(candidateRepository).findAll();
        assertThat(result).isEqualTo(candidateList);
    }

    @Test
    @DisplayName("Creating a candidate")
    void saveCandidate() throws  RecordNotFoundException{
//        Optional<String > empty = Optional.empty();

        when(positionRepository.findById(1L)).thenReturn(Optional.ofNullable(position1));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate1);

        Candidate result = candidateService.saveCandidate(candidate1,1L);

        //Candidate error = candidateService.findCandidateById(1L);
        verify(positionRepository).findById(1L);
        verify(candidateRepository).save(candidate1);

        assertThat(result).isEqualTo(candidate1);
        assertThat(result.getPosition()).isEqualTo(position1);
//        assertFalse(empty.isEmpty());
    }

    @Test
    @DisplayName("Updating a candidate ")
    void updateCandidate() throws RecordNotFoundException {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidate1));

        Candidate updateSample = Candidate.builder().candidateId(1L).name("updat3d").build();
                //new Candidate(1L, "updat3d","");
        when(candidateRepository.save(any(Candidate.class))).thenReturn(updateSample);

        Candidate result = candidateService.updateCandidate(updateSample, 1L);

        verify(candidateRepository).findById(1L);
        verify(candidateRepository).save(updateSample);
        assertThat(result).isEqualTo(candidate1);
    }

    @Test
    @DisplayName("Deleting a candidate")
    void deleteCandidate() throws RecordNotFoundException {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidate1));

        Candidate deleteSample = Candidate.builder().candidateId(1L).name("delet3d").build();
                //new Candidate(1L, "delet3d","");
        when(candidateRepository.save(any(Candidate.class))).thenReturn(deleteSample);

        String result = candidateService.deleteCandidate(1L);

        verify(candidateRepository).findById(1L);
        verify(candidateRepository).save(deleteSample);
        assertThat(result).isEqualTo("Record deleted.");
    }

    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws RecordNotFoundException {
        assertThrows(RecordNotFoundException.class, () -> {
            candidateService.findCandidateById(1L);
        }, "Record not found.");
    }
}
