package com.jrtg.eboto.controller;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Position;
import com.jrtg.eboto.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping
    public ResponseEntity<Iterable<Position>> findAll(){
        return new ResponseEntity<>(positionService.findAllPosition(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Position> findById(@PathVariable Long id){
        return new ResponseEntity<>(positionService.findPositionById(id),HttpStatus.OK);

    }
    @PostMapping("/{id}")
    public ResponseEntity<Position> save(@RequestBody Position position,@PathVariable  Long id){
        return new ResponseEntity<>(positionService.savePosition(position, id),HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return new ResponseEntity<>(positionService.deletePosition(id),HttpStatus.ACCEPTED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Position> update(@RequestBody Position position, @PathVariable Long id){
        return new ResponseEntity<>(positionService.updatePosition(position,id),HttpStatus.ACCEPTED);
    }
}
