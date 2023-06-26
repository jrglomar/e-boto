package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;

import java.util.List;

public interface ElectionService {
    Election findElectionById(Long id);
    
    List<Election> findAllElection();
    
    Election saveElection(Election election);
    
    Election updateElection(Election election, Long id);
    
    String deleteElection(Long id);
}
