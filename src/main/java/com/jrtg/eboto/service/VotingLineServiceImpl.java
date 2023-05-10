package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.VotingLine;
import com.jrtg.eboto.repository.VotingLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingLineServiceImpl implements VotingLineService {
    @Autowired
    VotingLineRepository votingLineRepository;
    
    @Override
    public VotingLine findVotingLineById(Long id){
        return votingLineRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<VotingLine> findAllVotingLine() {
        return votingLineRepository.findAll();
    }

    @Override
    public VotingLine saveVotingLine(VotingLine votingLine) {
        return votingLineRepository.save(votingLine);
    }

    @Override
    public VotingLine updateVotingLine(VotingLine votingLineRequest, Long id){
        VotingLine votingLineFound = votingLineRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found."));
        
        votingLineFound.setVotingLineTitle(votingLineRequest.getVotingLineTitle());
        
        return votingLineRepository.save(votingLineFound);
    }

    @Override
    public String deleteVotingLine(Long id) throws RecordNotFoundException {
        VotingLine votingLineFound = votingLineRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
        
        votingLineFound.setVotingLineTitle("delet3d");
        votingLineRepository.save(votingLineFound);
        
        return "Record deleted.";
    }
}
