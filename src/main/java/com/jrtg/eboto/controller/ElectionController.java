package com.jrtg.eboto.controller;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Election;
import com.jrtg.eboto.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elections")
public class ElectionController {
    
    @Autowired
    ElectionService electionService;

    @GetMapping
    public ResponseEntity<Iterable<Election>> findAll() {
        return new ResponseEntity<>(electionService.findAllElection(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Election> findById(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(electionService.findElectionById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Election> save(@RequestBody Election election) {
        return new ResponseEntity<>(electionService.saveElection(election), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Election> update(@RequestBody Election election, @PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(electionService.updateElection(election, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(electionService.deleteElection(id), HttpStatus.ACCEPTED);
    }
}
