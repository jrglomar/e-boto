package com.jrtg.eboto.repository;

import com.jrtg.eboto.model.VotingLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingLineRepository extends JpaRepository<VotingLine, Long> {

}
