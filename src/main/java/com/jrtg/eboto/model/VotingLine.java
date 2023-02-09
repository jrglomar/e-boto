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
public class VotingLine extends BaseAuditClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingLineId;

    @Column(length = 100, nullable = false)
    private String votingLineTitle;

}
