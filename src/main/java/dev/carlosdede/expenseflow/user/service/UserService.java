package dev.carlosdede.expenseflow.user.service;

import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.entity.AddressEntity;
import dev.carlosdede.expenseflow.address.mapper.AddressMapper;
import dev.carlosdede.expenseflow.address.repository.AddressRepository;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import dev.carlosdede.expenseflow.user.mapper.UserMapper;
import dev.carlosdede.expenseflow.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;


    public UserService(UserRepository userRepository, UserMapper mapper){
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public ResponseEntity<UserResponseDTO> create(UserCreateRequestDTO userCreateRequestDTO){
        if(userRepository.existsByEmail(userCreateRequestDTO.email())){
           throw new RuntimeException("Email já está em uso");
        }

        UserEntity user = mapper.toEntity(userCreateRequestDTO);
        UserEntity savedUser = userRepository.save(user);
        return null;
    }


}
