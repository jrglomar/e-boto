package com.jrtg.eboto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Candidate extends BaseAuditClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "candidate_id")
    private Long candidateId;

    //    @Column(length = 100, nullable = false)
//    private String candidateName;
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;


    //    @JoinColumn(name = "election_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id",updatable = false)
    private Position position;


}
