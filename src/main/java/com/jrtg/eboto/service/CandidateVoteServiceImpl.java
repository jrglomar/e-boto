package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.model.CandidateVote;
import com.jrtg.eboto.model.User;
import com.jrtg.eboto.repository.CandidateRepository;
import com.jrtg.eboto.repository.CandidateVoteRepository;
import com.jrtg.eboto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateVoteServiceImpl implements CandidateVoteService {
    @Autowired
    CandidateVoteRepository candidateVoteRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Override
    public CandidateVote findCandidateVoteById(Long id) {
        return candidateVoteRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<CandidateVote> findAllCandidateVote() {
        return candidateVoteRepository.findAll();
    }

    @Override
    public CandidateVote saveCandidateVote(CandidateVote candidateVote, Long userId, Long candidateId) {
        Optional<User> userFound = userRepository.findById(userId);
        Optional<Candidate> candidateFound = candidateRepository.findById(candidateId);
        if (candidateFound.isEmpty() || userFound.isEmpty()) {
            throw new RecordNotFoundException("Record not found.");
        }
        candidateVote.setUser(userFound.get());
        candidateVote.setCandidate(candidateFound.get());
        return candidateVoteRepository.save(candidateVote);
    }

    @Override
    public CandidateVote updateCandidateVote(CandidateVote candidateVote, Long id) {
        CandidateVote candidateFound = candidateVoteRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        candidateFound.setVoteReference(candidateVote.getVoteReference());
        return candidateVoteRepository.save(candidateFound);
    }

    @Override
    public String deleteCandidateVote(Long id) {
        CandidateVote candidateFound = candidateVoteRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        candidateFound.setVoteReference("delet3d");
        candidateVoteRepository.save(candidateFound);
        return "Record deleted.";
    }
}
