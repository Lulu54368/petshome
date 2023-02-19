package com.itproject.petshome.repository;

import com.itproject.petshome.dto.UserCodeDTO;
import com.itproject.petshome.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
@Data
@AllArgsConstructor
public class UserCodeRepositoryImpl implements UserCodeRepository{

    private RedisTemplate redisTemplate;

    private static final String KEY = "CodeById";
    private static final String KEY2 = "StoreByCode";
    @Override
    public boolean save(UserCodeDTO userCodeDTO) {
        try {
            redisTemplate.opsForHash().put(KEY, Long.toString(userCodeDTO.getUserId()), userCodeDTO);
            redisTemplate.opsForHash().put(KEY2, userCodeDTO.getVerificationCode(), userCodeDTO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserCodeDTO findById(Long id) {

        return (UserCodeDTO)redisTemplate.opsForHash().get(KEY,Long.toString(id));

    }

    @Override
    public UserCodeDTO findByCode(String code) {

        return (UserCodeDTO)redisTemplate.opsForHash().get(KEY2, code);
    }

    @Override
    public boolean update(Long id, UserCodeDTO user) {
        try {
            redisTemplate.opsForHash().put(KEY, Long.toString(id), user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(long id) {
        try {
            redisTemplate.opsForHash().delete(KEY,Long.toString(id));
            redisTemplate.opsForHash().delete(KEY2, Long.toString(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}