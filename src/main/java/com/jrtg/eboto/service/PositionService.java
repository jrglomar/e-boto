package com.jrtg.eboto.service;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Position;

import java.util.List;

public interface PositionService {
    Position findPositionById(Long id);

    List<Position> findAllPosition();

    Position savePosition(Position position, Long employeeId);

    Position updatePosition(Position position, Long id);
    String deletePosition(Long id);

}
