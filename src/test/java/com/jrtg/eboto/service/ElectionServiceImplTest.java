package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;
import com.jrtg.eboto.repository.ElectionRepository;
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
class ElectionServiceImplTest {

    @Mock
    ElectionRepository electionRepository;

    @InjectMocks
    ElectionService electionService = new ElectionServiceImpl();

    Election election1;
    Election election2;
    Election election3;
    List<Election> electionList;

    @BeforeEach
    void setup() {
        election1 = Election.builder().electionId(1L).title("Raven").dateEnd("09/05/23").description("any").status(false).build();

        election2 = Election.builder().electionId(1L).title("AJ").dateEnd("09/05/23").description("any").status(false).build();

        election3 = Election.builder().electionId(1L).title("Baqui").dateEnd("09/05/23").description("any").status(false).build();

        electionList = List.of(election1, election2, election3);
    }

    @Test
    @DisplayName("Finding election by id")
    void findElectionById(){
        when(electionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(election1));

        Election result = electionService.findElectionById(1L);

        verify(electionRepository).findById(1L);
        assertThat(result).isEqualTo(election1);
    }

    @Test
    @DisplayName("Finding all election")
    void findAllElection() {
        when(electionRepository.findAll()).thenReturn(electionList);

        List<Election> result = electionService.findAllElection();

        verify(electionRepository).findAll();
        assertThat(result).isEqualTo(electionList);
    }

    @Test
    @DisplayName("Creating a election")
    void saveElection() {
        when(electionRepository.save(any(Election.class))).thenReturn(election1);

        Election result = electionService.saveElection(election1);

        verify(electionRepository).save(election1);
        assertThat(result).isEqualTo(election1);
    }

    //tanong bukas
    @Test
    @DisplayName("Updating a election ")
    void updateElection() {
        when(electionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(election1));

        Election updateSample = new Election();//election1,"1L");
        when(electionRepository.save(any(Election.class))).thenReturn(updateSample);

        Election result = electionService.updateElection(updateSample, 1L);

        verify(electionRepository).findById(1L);
        verify(electionRepository).save(updateSample);
        assertThat(result).isEqualTo(election1);
    }

    @Test
    @DisplayName("Deleting a election")
    void deleteElection(){
        when(electionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(election1));

        Election deleteSample = new Election();//1L, "delet3d");
        when(electionRepository.save(any(Election.class))).thenReturn(deleteSample);

        String result = electionService.deleteElection(1L);

        verify(electionRepository).findById(1L);
        verify(electionRepository).save(deleteSample);
        assertThat(result).isEqualTo("Record deleted.");
    }

    @Test
    @DisplayName("Record not found")
    void recordNotFound() throws RecordNotFoundException {
        assertThrows(RecordNotFoundException.class, () -> {
            electionService.findElectionById(1L);
        }, "Record not found.");
    }
}
