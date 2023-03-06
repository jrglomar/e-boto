package com.jrtg.eboto.controller;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.VotingLine;
import com.jrtg.eboto.service.VotingLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votinglines")
public class VotingLineController {

    @Autowired
    VotingLineService votingLineService;

    @GetMapping
    public ResponseEntity<Iterable<VotingLine>> findAll() {
        return new ResponseEntity<>(votingLineService.findAllVotingLine(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotingLine> findById(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(votingLineService.findVotingLineById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VotingLine> save(@RequestBody VotingLine votingLine) {
        return new ResponseEntity<>(votingLineService.saveVotingLine(votingLine), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotingLine> update(@RequestBody VotingLine votingLine, @PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(votingLineService.updateVotingLine(votingLine, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(votingLineService.deleteVotingLine(id), HttpStatus.ACCEPTED);
    }
}
