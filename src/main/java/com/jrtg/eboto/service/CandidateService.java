package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;

public interface CandidateService {
    Candidate findCandidateById(Long id) throws RecordNotFoundException;
    
    Iterable<Candidate> findAllCandidate();
    
    Candidate saveCandidate(Candidate candidate);
    
    Candidate updateCandidate(Candidate candidate, Long id) throws RecordNotFoundException;
    
    String deleteCandidate(Long id) throws RecordNotFoundException;
}
