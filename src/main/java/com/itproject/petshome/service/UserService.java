package com.itproject.petshome.service;

import com.itproject.petshome.config.ApplicationProperties;
import com.itproject.petshome.dto.RegisterInput;
import com.itproject.petshome.dto.UserDTO;
import com.itproject.petshome.exception.UserAlreadyExistException;
import com.itproject.petshome.exception.UserCodeNotFoundException;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.UserDetails;
import com.itproject.petshome.repository.UserCodeRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

import java.util.Optional;

import static java.lang.String.format;
@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final ApplicationProperties properties;
    private final SessionService sessionService;

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
    public UserDTO registerUser(RegisterInput input, String siteURL) throws UserAlreadyExistException, MessagingException,
            UserCodeNotFoundException, UserNotFoundException, UserAlreadyExistException.DuplicatePhoneNumber {
        if (userRepository.findByEmail(input.getEmail()).isPresent()
                || userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        if(userRepository.findByPhoneNumber(input.getPhoneNumber()).isPresent())
            throw new UserAlreadyExistException.DuplicatePhoneNumber();



        User user = mapper.map(input, User.class);
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCurrency(properties.getDefaultCurrency());
        String randomCode = UUID.randomUUID().toString();
        user.setVerified(false);
        userRepository.save(user);

        user = userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(UserNotFoundException::new);
        UserCodeDTO userRegistrationCodeDTO =
                new UserCodeDTO();
        userRegistrationCodeDTO.setUserId(user.getId());
        userRegistrationCodeDTO.setVerificationCode(randomCode);
        //expire after 5 minutes
        //userRegistrationCodeDTO.setTokenExpires(Instant.now().plus(5, ChronoUnit.MINUTES));
        userCodeRepository.save(userRegistrationCodeDTO);

        emailService.sendEmail(user, siteURL);
        return mapper.map(user, UserDTO.class);
    }



    public boolean verify(String verificationCode) throws UserCodeNotFoundException, UserNotFoundException, TimeOutException {
        if(userCodeRepository.findByCode(verificationCode) == null)
            return false;
        UserCodeDTO userCode = userCodeRepository
                .findByCode(verificationCode);
        /*if(userCode.isTokenExpired() == true){
            userCodeRepository.delete(userCode.getUserId());
            throw new TimeOutException();
        }*/

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

}





}
