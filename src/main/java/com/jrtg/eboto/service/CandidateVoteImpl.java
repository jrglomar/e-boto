package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.CandidateVote;
import com.jrtg.eboto.repository.CandidateVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateVoteImpl implements CandidateVoteService{
    @Autowired
    CandidateVoteRepository candidateVoteRepository;
    @Override
    public CandidateVote findCandidateVoteById(Long id){
        return candidateVoteRepository.findById(id).orElseThrow(()->new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<CandidateVote> findAllCandidateVote() {
        return candidateVoteRepository.findAll();
    }

    @Override
    public CandidateVote saveCandidateVote(CandidateVote candidateVote) {
        return candidateVoteRepository.save(candidateVote);
    }

    @Override
    public CandidateVote updateCandidateVote(CandidateVote candidateVote, Long id){
       CandidateVote candidateFound = candidateVoteRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("Record not found."));
       candidateFound.setVoteReference(candidateVote.getVoteReference());
        return candidateVoteRepository.save(candidateFound);
    }

    @Override
    public String deleteCandidateVote(Long id){
        CandidateVote candidateFound = candidateVoteRepository.findById(id).orElseThrow(()-> new  RecordNotFoundException("Record not found."));
        candidateFound.setVoteReference("dele3d");
        candidateVoteRepository.save(candidateFound);
        return "Record deleted";
    }
}
