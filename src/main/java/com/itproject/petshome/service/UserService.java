package com.itproject.petshome.service;

import com.itproject.petshome.config.ApplicationProperties;
import com.itproject.petshome.dto.*;
import com.itproject.petshome.exception.UserAlreadyExistException;
import com.itproject.petshome.exception.UserCodeNotFoundException;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.UserDetails;
import com.itproject.petshome.model.enums.Adopted;
import com.itproject.petshome.repository.UserCodeRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
@Data
@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final ApplicationProperties properties;
    private final SessionService sessionService;
    private ModelMapper mapper;
    private UserRepository userRepository;

    private EmailService emailService;
    private UserCodeRepository userCodeRepository;



    public UserDetails getUserDetailsByEmail(String email) throws UsernameNotFoundException {
        return UserDetails.of(userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("User: %s, not found", email)
                        )
                ));
    }

    /**
     * Crete a new user entity from register input.
     *
     * @param input
     * @return
     * @throws UserAlreadyExistException User with the username or email exist
     */
    @Transactional
    public UserDTO registerUser(RegisterInput input, String siteURL) throws MessagingException,
            UserCodeNotFoundException, UserNotFoundException, UserAlreadyExistException{
        if (userRepository.findByEmail(input.getEmail()).isPresent()
                || userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }



        User user = mapper.map(input, User.class);
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        String randomCode = UUID.randomUUID().toString();
        user.setVerified(false);
        userRepository.save(user);

        user = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(UserNotFoundException::new);
        UserCodeDTO userRegistrationCodeDTO =
                new UserCodeDTO();
        userRegistrationCodeDTO.setUserId(user.getId());
        userRegistrationCodeDTO.setVerificationCode(randomCode);

        userCodeRepository.save(userRegistrationCodeDTO);

        emailService.sendEmail(user, siteURL);
        return mapper.map(user, UserDTO.class);
    }



    public boolean verify(String verificationCode) throws UserCodeNotFoundException, UserNotFoundException {
        if(userCodeRepository.findByCode(verificationCode) == null)
            return false;
        UserCodeDTO userCode = userCodeRepository
                .findByCode(verificationCode);

        Optional<User> oUser = userRepository
                .findById(userCode.getUserId());
        if(oUser.isPresent() == false){
            userCodeRepository.delete(userCode.getUserId());
            throw new UserNotFoundException();
        }
        User user = oUser.get();

        userCodeRepository.delete(userCode.getUserId());
        user.setVerified(true);
        userRepository.save(user);
        return true;


    }
    public UserDTO viewUserInformation(User user) {
        return null;
    }
    public UserDTO updateUserInformation(UpdateUserInput input) {
        return null;
    }
    public UserAdoptPetDTO addAdoptionApplication
            (AdoptionApplicationDTO adoptionApplicationDTO) {
        return null;
    }
    public List<AdoptionApplicationDTO> viewAdoptionApplications(Adopted adopted) {
        return null;
    }

}






