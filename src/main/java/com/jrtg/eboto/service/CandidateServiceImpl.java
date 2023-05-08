package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.model.Position;
import com.jrtg.eboto.repository.CandidateRepository;
import com.jrtg.eboto.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    PositionRepository positionRepository;

    @Override
    public Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<Candidate> findAllCandidate() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate saveCandidate(Candidate candidate, Long id) {
        Optional<Position> candidateFound = positionRepository.findById(id);
        if (candidateFound.isEmpty()) {
            throw new RecordNotFoundException("Record not found");
        }
        candidate.setPosition(candidateFound.get());
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Candidate candidateRequest, Long id) {
        Candidate candidateFound = candidateRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        candidateFound.setName(candidateRequest.getName());
        candidateFound.setDescription(candidateRequest.getDescription());
        return candidateRepository.save(candidateFound);
    }


    @Override
    public String deleteCandidate(Long id) {
        Candidate candidateFound = candidateRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        candidateFound.setName("delet3d");
        candidateRepository.save(candidateFound);

        return "Record deleted.";
    }
}
