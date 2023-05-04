package com.jrtg.eboto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Election extends BaseAuditClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "election_id")
    private Long electionId;
    
    @Column(length = 100, nullable = false)
    private String electionName;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Position> positionList;
    
}
