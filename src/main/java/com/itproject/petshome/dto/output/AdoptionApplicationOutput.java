package com.itproject.petshome.dto.output;

import lombok.Data;

@Data
public class AdoptionApplicationOutput {
    private String lastname;
    private String firstname;
    private Long userId;
    private Long petId;
    private String city;
    private String email;
    private String reason;
}
