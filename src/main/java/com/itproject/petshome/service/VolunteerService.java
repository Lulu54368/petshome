package com.itproject.petshome.service;

import com.itproject.petshome.dto.VolunteerApplicationDTO;
import com.itproject.petshome.dto.input.VolunteerApplicationInput;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.exception.VolunteerApplicationAlreadyExistException;
import com.itproject.petshome.exception.VolunteerApplicationNotFound;
import com.itproject.petshome.mapper.VolunteerApplicationMapper;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.VolunteerApplication;
import com.itproject.petshome.repository.UserRepository;
import com.itproject.petshome.repository.VolunteerApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@AllArgsConstructor
public class VolunteerService {
    VolunteerApplicationMapper volunteerApplicationMapper;

    VolunteerApplicationRepository volunteerApplicationRepository;

    SessionService sessionService;

    UserRepository userRepository;
    public VolunteerApplicationDTO addVolunteerApplication(VolunteerApplicationInput volunteerApplicationDTO)
    throws UserNotFoundException, VolunteerApplicationAlreadyExistException
    {
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if(volunteerApplicationRepository.existsByUser(user) == true) {
            throw new VolunteerApplicationAlreadyExistException();
        }
        VolunteerApplication volunteerApplication = new VolunteerApplication();
        volunteerApplication.setReason(volunteerApplication.getReason());
        volunteerApplication.setTimeApplied(new Timestamp(new Date().getTime()));
        volunteerApplication.setUser(user);
        volunteerApplication = volunteerApplicationRepository.save(volunteerApplication);

        return volunteerApplicationMapper.toDto(volunteerApplication);
    }

    public VolunteerApplicationDTO deleteVolunteerApplication()
        throws UserNotFoundException, VolunteerApplicationNotFound
    {
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        VolunteerApplication volunteerApplication =
                volunteerApplicationRepository
                        .findByUser(user)
                        .orElseThrow(VolunteerApplicationNotFound::new);
        volunteerApplicationRepository.delete(volunteerApplication);
        return volunteerApplicationMapper.toDto(volunteerApplication);
    }


    public VolunteerApplicationDTO getVolunteerApplication()
        throws UserNotFoundException, VolunteerApplicationNotFound
    {
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        VolunteerApplication volunteerApplication =
                volunteerApplicationRepository
                        .findByUser(user)
                        .orElseThrow(VolunteerApplicationNotFound::new);
        return volunteerApplicationMapper.toDto(volunteerApplication);
    }
}
