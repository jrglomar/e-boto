package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;
import com.jrtg.eboto.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    @Autowired
    ElectionRepository electionRepository;

    @Override
    public Election findElectionById(Long id) {
        return electionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<Election> findAllElection() {
        return electionRepository.findAll();
    }

    @Override
    public Election saveElection(Election election) {
        return electionRepository.save(election);
    }

    @Override
    public Election updateElection(Election election, Long id) {
        Election electionFound = electionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found."));
        electionFound.setTitle(election.getTitle());
        electionFound.setDescription(election.getDescription());
        electionFound.setStatus(election.isStatus());
        electionFound.setDateEnd(election.getDateEnd());
        return electionRepository.save(electionFound);
    }

    @Override
    public String deleteElection(Long id) {
        Election electionFound = electionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record to delete not found."));
        electionFound.setTitle("delet3d");
        electionRepository.save(electionFound);
        return "Record deleted.";
    }
}
