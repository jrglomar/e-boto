package com.jrtg.eboto.controller;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.Candidate;
import com.jrtg.eboto.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @GetMapping
    public ResponseEntity<Iterable<Candidate>> findAll() {
        return new ResponseEntity<>(candidateService.findAllCandidate(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> findById(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(candidateService.findCandidateById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Candidate> save(@RequestBody Candidate candidate) {
        return new ResponseEntity<>(candidateService.saveCandidate(candidate), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Candidate> update(@RequestBody Candidate candidate, @PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(candidateService.updateCandidate(candidate, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(candidateService.deleteCandidate(id), HttpStatus.ACCEPTED);
    }
}
