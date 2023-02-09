package com.jrtg.eboto.repository;

import com.jrtg.eboto.model.VotingLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingLineRepository extends CrudRepository<VotingLine, Long> {

}
