package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    CandidateRepository candidateRepository;
    
    @Override
    public Candidate findCandidateById(Long id) throws RecordNotFoundException {
        return candidateRepository.findById(1L).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public Iterable<Candidate> findAllCandidate() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Candidate candidateRequest, Long id) throws RecordNotFoundException {
        Candidate candidateFound = candidateRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        
        candidateFound.setCandidateName(candidateRequest.getCandidateName());
        return candidateRepository.save(candidateFound);
    }
    

    @Override
    public String deleteCandidate(Long id) throws RecordNotFoundException {
        Candidate candidateFound = candidateRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        candidateFound.setCandidateName("delet3d");
        candidateRepository.save(candidateFound);
        
        return "Record deleted.";
    }
}
