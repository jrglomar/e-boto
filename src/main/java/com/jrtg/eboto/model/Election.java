package com.jrtg.eboto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
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

    //    @Column(length = 100, nullable = false)
//    private String electionName;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 100, nullable = false)
    private String description;

    @Column(name = "statuses")
    private boolean status;
//    private boolean status = true;
//    @UpdateTimestamp
//    @LastModifiedDate
//    @Column(name = "date_end")s
//    private Date dateEnd;

    @Column(name = "date_end")
    private String dateEnd;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Position> positionList;

}
