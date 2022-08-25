package com.itproject.petshome.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
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
    @Column(name = "dob", length = 10)
    private String dob;
    @Column(name = "firstname", length = 25, nullable = false)
    private String firstname;
    @Column(name = "lastname", length = 25, nullable = false)
    private String lastname;
    @Column(name = "bio")
    private String bio;
    @Column(name = "verified")
    private Boolean verified;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VolunteerApplication> volunteerApplications = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AdoptionApplication> adoptionApplications=  new HashSet<>();
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "track",
            joinColumns = { @JoinColumn(name = "applicant_id") },
            inverseJoinColumns = { @JoinColumn(name = "pet_id") })
    private Set<Pet> pets = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Donation> donations = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAdoptPet> userAdoptPets= new HashSet<>();



}
