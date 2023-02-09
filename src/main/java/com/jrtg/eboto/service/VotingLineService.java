package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.VotingLine;

public interface VotingLineService {

    VotingLine findVotingLineById(Long id) throws RecordNotFoundException;

    Iterable<VotingLine> findAllVotingLine();

    VotingLine saveVotingLine(VotingLine votingLine);

    VotingLine updateVotingLine(VotingLine votingLine, Long id) throws RecordNotFoundException;

    String deleteVotingLine(Long id) throws RecordNotFoundException;

}
