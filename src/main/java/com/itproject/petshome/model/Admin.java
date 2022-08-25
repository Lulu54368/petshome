package com.itproject.petshome.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@Data
public class Admin {
    @Id
    private long id;
    @Column(name = "username")
    private String username;



}
