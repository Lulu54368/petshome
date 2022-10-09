package com.itproject.petshome.service;

import com.itproject.petshome.dto.AdminDTO;
import com.itproject.petshome.dto.AdoptionApplicationDTO;
import com.itproject.petshome.dto.input.AdminLogin;
import com.itproject.petshome.dto.input.RegisterInput;
import com.itproject.petshome.dto.input.UpdateAdoptionApplicationInput;
import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.exception.AdoptionApplicationNotFound;
import com.itproject.petshome.exception.UserAlreadyExistException;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.mapper.AdminMapper;
import com.itproject.petshome.mapper.AdoptionApplicationMapper;
import com.itproject.petshome.model.Admin;
import com.itproject.petshome.model.AdminDetail;
import com.itproject.petshome.model.AdoptionApplication;
import com.itproject.petshome.model.UserAdoptPet;
import com.itproject.petshome.model.enums.ApplicationStatus;
import com.itproject.petshome.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

import static java.lang.String.format;

@Data
@Service
@AllArgsConstructor
public class AdminService {
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final VolunteerApplicationRepository volunteerApplicationRepository;
    private final PasswordEncoder passwordEncoder;
    public final AdoptionApplicationMapper adoptionApplicationMapper;
    private final UserAdoptPetRepository userAdoptPetRepository;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    public VolunteerApplicationDTO viewVolunteerApplication(ApplicationStatus status, Long id) {
        return null;
    }


    public AdoptionApplicationDTO updateAdoptionApplication
            (ApplicationStatus applicationStatus, Long applicationId) throws AdoptionApplicationNotFound {
        AdoptionApplication adoptionApplication =
                adoptionApplicationRepository.findById(applicationId)
                .orElseThrow(AdoptionApplicationNotFound:: new);
        if(applicationStatus.equals(ApplicationStatus.COMPLETE)){
            UserAdoptPet userAdoptPet = new UserAdoptPet();
            userAdoptPet.setUser(adoptionApplication.getUser());
            userAdoptPet.setPet(adoptionApplication.getPet());
            userAdoptPet.setTimeAdopted(new Timestamp(new Date().getTime()));
            userAdoptPetRepository.save(userAdoptPet);
        }
        adoptionApplication.setApplicationStatus(applicationStatus);

        return adoptionApplicationMapper.toDto(adoptionApplication);

    }

    public UserDetails getAdminDetailsByUsername(String s) {
        return AdminDetail.of(adminRepository
                .findByUsername(s)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("User: %s, not found", s)
                        )
                ));
    }

    public AdminDTO registerUser(AdminLogin input) throws UserAlreadyExistException {
        if(adminRepository.findByUsername(input.getUsername()).isPresent() == true){
            throw new UserAlreadyExistException();
        }
        Admin admin = new Admin();
        admin.setUsername(input.getUsername());
        admin.setPassword(passwordEncoder.encode(input.getPassword()));
        adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }
}
