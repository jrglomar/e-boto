package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.VotingLine;

import java.util.List;

public interface VotingLineService {

    VotingLine findVotingLineById(Long id);

    List<VotingLine> findAllVotingLine();

    VotingLine saveVotingLine(VotingLine votingLine);

    VotingLine updateVotingLine(VotingLine votingLine, Long id);

    String deleteVotingLine(Long id);

}
