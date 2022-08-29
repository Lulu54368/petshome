package com.itproject.petshome.service;

import com.itproject.petshome.dto.PetDTO;
import com.itproject.petshome.dto.PetInput;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    public PetDTO addPet(PetInput input) {
        return new PetDTO();
    }
}
