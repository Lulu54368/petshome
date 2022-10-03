package com.itproject.petshome.dto.input;

import com.itproject.petshome.model.enums.Payment;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.YearMonth;
import java.util.Date;

@Data
public class DonationInput {
    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    String phoneCountryCode;

    @NotEmpty
    int phoneNumber;

    @NotEmpty
    int donationAmount;

    @NotEmpty
    Payment payment;

    @NotEmpty
    int cardNumber;

    @NotEmpty
    int expiryMonth;

    @NotEmpty
    int expiryYear;

    @NotEmpty
    int cvv;

}
