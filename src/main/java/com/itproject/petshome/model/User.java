package com.itproject.petshome.model;

import com.itproject.petshome.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    long id;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;
    @Column(name = "address", length = 100)
    private String address;
    @Column(name = "city", length = 45)
    private String city;
    @Column(name = "country", length = 45)
    private String country;
    @Column(name = "dob", length = 10)
    private String dob;
    @Column(name = "firstname", length = 25, nullable = false)
    private String firstname;
    @Column(name = "lastname", length = 25, nullable = false)
    private String lastname;
    @Column(name = "bio")
    private String bio;
    @Column(name = "identification")
    private String identification;
    @Column(name = "verified")
    private Boolean verified;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VolunteerApplication> volunteerApplications = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdoptionApplication> adoptionApplications=  new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Donation> donations = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAdoptPet> userAdoptPets= new HashSet<>();
    @Column(name = "role", nullable = false)
    private UserRole userRole = UserRole.VISITOR;




}
