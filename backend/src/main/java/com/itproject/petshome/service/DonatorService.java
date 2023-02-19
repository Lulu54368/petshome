package com.itproject.petshome.service;

import com.itproject.petshome.dto.DonationDTO;
import com.itproject.petshome.dto.input.DonationInput;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.mapper.DonationMapper;
import com.itproject.petshome.model.Donation;
import com.itproject.petshome.model.User;
import com.itproject.petshome.repository.DonationRepository;
import lombok.AllArgsConstructor;
import org.joda.time.YearMonth;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonatorService {
    UserService userService;
    SessionService sessionService;
    DonationRepository donationRepository;

    DonationMapper donationMapper;
    public DonationDTO addDonation(@Valid DonationInput donationInput) throws UserNotFoundException{

        User user = this.sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        Donation donation  = new Donation();
        donation.setAmount(donationInput.getDonationAmount());
        donation.setTimestamp(new Timestamp(new Date().getTime()));
        donation.setUser(user);
        donation = donationRepository.save(donation);
        return donationMapper.toDto(donation);
    }

    public List<DonationDTO> viewDonation() throws UserNotFoundException {
        User user = sessionService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return donationRepository.findByUser(user)
                .stream()
                .map(donation -> donationMapper.toDto(donation))
                .collect(Collectors.toList());
    }
}
