package com.jrtg.eboto.repository;

import com.jrtg.eboto.model.CandidateVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateVoteRepository extends JpaRepository<CandidateVote, Long> {
}
