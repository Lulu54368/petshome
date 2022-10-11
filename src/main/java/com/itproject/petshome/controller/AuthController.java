package com.itproject.petshome.controller;


import com.itproject.petshome.config.ApplicationProperties;
import com.itproject.petshome.config.UserAuthenticationProvider;
import com.itproject.petshome.dto.*;

import com.itproject.petshome.dto.input.LoginInput;
import com.itproject.petshome.dto.input.RegisterInput;
import com.itproject.petshome.dto.output.AuthorizeOutput;
import com.itproject.petshome.exception.UserAlreadyExistException;
import com.itproject.petshome.exception.UserCodeNotFoundException;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.model.UserDetails;
import com.itproject.petshome.repository.UserCodeRepository;
import com.itproject.petshome.repository.UserRepository;
import com.itproject.petshome.service.UserService;
import com.itproject.petshome.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Authentication")
@RestController
@RequestMapping(path = "/api/v1/auth")
@Validated
@AllArgsConstructor
public class AuthController {

    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ApplicationProperties properties;
    private UserRepository userRepository;
    private UserCodeRepository userCodeRepository;
    @PostMapping("/user/login")
    @ApiResponse(description = "Login successful", responseCode = "200")
    @ApiResponse(description = "Username or password incorrect", responseCode = "401")
    public AuthorizeOutput login(@RequestBody @Valid LoginInput request) {
        Authentication authenticate = ((UserAuthenticationProvider) authenticationProvider)
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()
                        )
                );

        UserDetails user = (UserDetails) authenticate.getPrincipal();
        return buildAuthorizeOutput(user);
    }

    @PostMapping("/user/register")
    @ApiResponse(description = "User Created", responseCode = "201")
    @ApiResponse(description = "Failed, username or email already exist", responseCode = "409")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody @Valid RegisterInput input,
                            HttpServletRequest request)
            throws UserAlreadyExistException, MessagingException, UserCodeNotFoundException, UserNotFoundException{

        final UserDTO userDTO = userService.registerUser(input, getSiteURL(request));
        return userDTO;
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    private AuthorizeOutput buildAuthorizeOutput(UserDetails user) {
        final AuthorizeOutput output = new AuthorizeOutput();
        output.setEmail(user.getUsername());
        output.setUserId(user.getId());
        output.setToken(jwtTokenUtil.generateToken(user));
        output.setExpiresIn(properties.getAccessTokenValiditySeconds());
        return output;
    }

    @GetMapping("/verify")
    @ApiResponse(description = "Verify results given", responseCode = "201")
    @ApiResponse(description = "User or usercode not exist", responseCode = "404")
    @ResponseStatus(HttpStatus.CREATED)
    public String verifyUser(@Param("code") String code, Model model)
            throws UserCodeNotFoundException, UserNotFoundException{

        UserCodeDTO userCode = userCodeRepository
                .findByCode(code);


        User user = userRepository
                .findById(userCode.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Boolean verified = userService.verify(code);
        String pageTitle = verified? "Verification Succeed!": "Verification Failed!";
        model.addAttribute("pageTitle", pageTitle);
        if(verified) buildAuthorizeOutput(UserDetails.of(user));
        return "registration/" + (verified? "verify_success": "verify_failed");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "email or password incorrect")
    public void handleBadCredentialException(BadCredentialsException e) {
    }
}
