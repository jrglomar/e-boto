package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;
import com.jrtg.eboto.model.Position;
import com.jrtg.eboto.repository.ElectionRepository;
import com.jrtg.eboto.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    ElectionRepository electionRepository;

    @Override
    public Position findPositionById(Long id){
        return positionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Record not found."));
    }

    @Override
    public List<Position> findAllPosition() {
        return positionRepository.findAll();
    }

    @Override
    public Position savePosition(Position position, Long electionId){
        Optional<Election> electionFound = electionRepository.findById(electionId);
        if (electionFound.isEmpty()){
            throw new RecordNotFoundException("Record not found.");
        }
        position.setElection(electionFound.get());
        return positionRepository.save(position);
    }

    @Override
    public Position updatePosition(Position position, Long id){
        Position positionFound = positionRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Record to update not found."));
        positionFound.setTitle(position.getTitle());
        return positionRepository.save(positionFound);
    }

    @Override
    public String deletePosition(Long id){
        Position positionFound = positionRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Record to delete not found."));
        positionFound.setTitle("delet3d");
        positionRepository.save(positionFound);
        return "Record deleted.";
    }
}
