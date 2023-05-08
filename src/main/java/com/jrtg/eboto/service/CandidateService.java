package com.jrtg.eboto.service;

import com.jrtg.eboto.model.Candidate;

import java.util.List;

public interface CandidateService {
    Candidate findCandidateById(Long id);
    
    List<Candidate> findAllCandidate();
    
    Candidate saveCandidate(Candidate candidate, Long id);
    
    Candidate updateCandidate(Candidate candidate, Long id);
    
    String deleteCandidate(Long id);
}
