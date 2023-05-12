package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.model.CandidateVote;
import com.jrtg.eboto.model.User;
import com.jrtg.eboto.repository.CandidateRepository;
import com.jrtg.eboto.repository.CandidateVoteRepository;
import com.jrtg.eboto.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CandidateVoteServiceImplTest {

    @Mock
    CandidateVoteRepository candidateVoteRepository;

    @InjectMocks
    CandidateVoteService candidateVoteService = new CandidateVoteServiceImpl();
    @Spy
    UserRepository userRepository;

    @Spy
    CandidateRepository candidateRepository;

    CandidateVote candidateVote1;
    CandidateVote candidateVote2;
    CandidateVote candidateVote3;
    User user1;

    Candidate candidate1;

    List<CandidateVote> candidateVoteList;

    @BeforeEach
    void setup() {
        candidateVote1 = CandidateVote.builder().candidateVoteId(1L).voteReference("President").build();
        candidateVote2 = CandidateVote.builder().candidateVoteId(2L).voteReference("Vice-President").build();
        candidateVote3 = CandidateVote.builder().candidateVoteId(3L).voteReference("Secretary").build();
        user1 = User.builder().userId(1L).email("ajgutierrez@gmail.com").middleName("C").lastName("Gutierrez").firstName("Alexander").password("updat3d").build();

        candidate1 = Candidate.builder().candidateId(1L).name("aj").description("Hello").build();

        candidateVoteList = List.of(candidateVote1, candidateVote2, candidateVote3);
    }

    @Test
    @DisplayName("Finding candidateVote by id")
    void findCandidateVoteById() throws RecordNotFoundException {
        when(candidateVoteRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidateVote1));

        CandidateVote result = candidateVoteService.findCandidateVoteById(1L);

        verify(candidateVoteRepository).findById(1L);
        assertThat(result).isEqualTo(candidateVote1);
    }

    @Test
    @DisplayName("Finding all candidatesVote")
    void findAllCandidateVote() throws RecordNotFoundException{
        when(candidateVoteRepository.findAll()).thenReturn(candidateVoteList);

        List<CandidateVote> result = candidateVoteService.findAllCandidateVote();

        verify(candidateVoteRepository).findAll();
        assertThat(result).isEqualTo(candidateVoteList);

    }

    @Test
    @DisplayName("Creating a candidateVote")
    void saveCandidateVote() throws RecordNotFoundException{

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        when(candidateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidate1));

        when(candidateVoteRepository.save(any(CandidateVote.class))).thenReturn(candidateVote1);

        CandidateVote result = candidateVoteService.saveCandidateVote(candidateVote1,1L,1L);

        verify(candidateRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(candidateVoteRepository).save(candidateVote1);

        assertThat(result).isEqualTo(candidateVote1);
        assertThat(result.getCandidate()).isEqualTo(candidate1);
        assertThat(result.getUser()).isEqualTo(user1);

    }
    @Test
    @DisplayName("Updating a candidateVote ")
    void updateCandidateVote() throws RecordNotFoundException {
        when(candidateVoteRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidateVote1));

        CandidateVote updateSample = CandidateVote
                .builder().candidateVoteId(1L)
                .voteReference("updat3d")
                .build();

        when(candidateVoteRepository.save(any(CandidateVote.class))).thenReturn(updateSample);

        CandidateVote result = candidateVoteService.updateCandidateVote(updateSample,1L);

        verify(candidateVoteRepository).findById(1L);
        verify(candidateVoteRepository).save(updateSample);

        assertThat(result).isEqualTo(candidateVote1);
    }
    @Test
    @DisplayName("Deleting a candidateVote")
    void deleteCandidate() throws RecordNotFoundException {
        when(candidateVoteRepository.findById(anyLong())).thenReturn(Optional.ofNullable(candidateVote2));

        CandidateVote deleteSample = CandidateVote
                .builder().candidateVoteId(2L)
                .voteReference("delet3d")
                .build();
        when(candidateVoteRepository.save(any(CandidateVote.class))).thenReturn(deleteSample);

        String result = candidateVoteService.deleteCandidateVote(2L);

        verify(candidateVoteRepository).findById(2L);
        verify(candidateVoteRepository).save(deleteSample);
        assertThat(result).isEqualTo("Record deleted.");


    }
    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws RecordNotFoundException{
        assertThrows(RecordNotFoundException.class,()->{
            candidateVoteService.findCandidateVoteById(1L);
        },"Record not found.");
    }



}
