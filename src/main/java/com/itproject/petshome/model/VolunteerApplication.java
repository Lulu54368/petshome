package com.itproject.petshome.model;

import com.itproject.petshome.model.enums.ApplicationStatus;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "volunteer_application")
public class VolunteerApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name= "status")
    private ApplicationStatus applicationStatus = ApplicationStatus.PENDING;
    @Column(name = "reason", length = 500)
    private String reason;
    @Column(name = "time_applied")
    private Timestamp timeApplied;
    @Column(name = "time_reviewed")
    private Timestamp timeReviewed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private User user;
}
