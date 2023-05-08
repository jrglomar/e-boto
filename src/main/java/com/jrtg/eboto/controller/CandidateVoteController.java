package com.jrtg.eboto.controller;

import com.jrtg.eboto.exception.RecordNotFoundException;
import com.jrtg.eboto.model.CandidateVote;
import com.jrtg.eboto.service.CandidateVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidatevote")
public class CandidateVoteController {
    @Autowired
    CandidateVoteService candidateVoteService;

    @GetMapping
    public ResponseEntity<Iterable<CandidateVote>> findAll(){
        return new ResponseEntity<>(candidateVoteService.findAllCandidateVote(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CandidateVote> findById(@PathVariable Long id){
        return new ResponseEntity<>(candidateVoteService.findCandidateVoteById(id),HttpStatus.OK);
    }
    @PostMapping("/{userId}/{candidateId}")
    public ResponseEntity<CandidateVote> save(@RequestBody CandidateVote candidateVote,@PathVariable Long userId, @PathVariable Long candidateId){
        return new ResponseEntity<>(candidateVoteService.saveCandidateVote(candidateVote,userId,candidateId),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CandidateVote> update(@RequestBody CandidateVote candidateVote, @PathVariable Long id){
        return new ResponseEntity<>(candidateVoteService.updateCandidateVote(candidateVote,id),HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return new ResponseEntity<>(candidateVoteService.deleteCandidateVote(id),HttpStatus.ACCEPTED);
    }

}
