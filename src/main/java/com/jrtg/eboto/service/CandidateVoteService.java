package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.CandidateVote;

import java.util.List;

public interface CandidateVoteService {
    CandidateVote findCandidateVoteById(Long id);

    List<CandidateVote> findAllCandidateVote();
    CandidateVote saveCandidateVote(CandidateVote candidateVote);

    CandidateVote updateCandidateVote(CandidateVote candidateVote, Long id);
    String deleteCandidateVote(Long id) throws RecordNotFoundException;
}
