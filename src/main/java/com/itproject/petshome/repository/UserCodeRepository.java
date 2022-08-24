package com.itproject.petshome.repository;

import com.itproject.petshome.dto.UserCodeDTO;
import com.itproject.petshome.model.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCodeRepository {
    boolean save(UserCodeDTO userCodeDTO);


    UserCodeDTO findById(Long id);
    UserCodeDTO findByCode(String code);
    boolean delete(long id);

    boolean update(Long id, UserCodeDTO user);
}
