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
@Table(name="users")
public class User extends BaseAuditClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false,name = "user_id")
    private Long userId;

//    @Column(length = 100, nullable = false)
//    private String userName;
//
//    @Column
//    private String userPassword;
    @Column(length = 100, nullable = false,name = "first_Name")
    private String firstName;
    @Column(length = 100, nullable = false,name = "middle_Name")
    private String middleName;
    @Column(length = 100, nullable = false,name = "last_Name")
    private String lastName;
    @Column(length = 100, nullable = false,name = "email")
    private String email;
    @Column(length = 100, nullable = false,name = "password")
    private String password;



    
}
