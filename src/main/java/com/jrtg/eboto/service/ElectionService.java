package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;

import java.util.List;

public interface ElectionService {
    Election findElectionById(Long id) throws RecordNotFoundException;
    
    List<Election> findAllElection();
    
    Election saveElection(Election election);
    
    Election updateElection(Election election, Long id) throws RecordNotFoundException;
    
    String deleteElection(Long id) throws RecordNotFoundException;
}
