package com.itproject.petshome.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column(name = "time")
    private Timestamp timestamp;
    @Column(name = "amount", nullable = false)
    private int amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation")
    private User user;
}
