package com.itproject.petshome.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class DonationDTO {
    private Long id;
    private Timestamp timestamp;
    private int amount;
    Long userId;
}
