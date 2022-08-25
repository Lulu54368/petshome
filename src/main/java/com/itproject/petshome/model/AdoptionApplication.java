package com.itproject.petshome.model;

import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.model.enums.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "adoption_application")
@Data
public class AdoptionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "time_applied")
    private Timestamp timestamp;
    @Column(name = "reason", length = 500)
    private String reason;
    @Column(name = "passport_number", length = 45, nullable = false)
    private String passport;
    @Column(name = "status", nullable = false)
    private ApplicationStatus applicationStatus = ApplicationStatus.PENDING;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @Column(name = "role")
    private UserRole userRole = UserRole.VISITOR;
}
