package dev.carlosdede.expenseflow.user.service;
import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import dev.carlosdede.expenseflow.address.service.AddressService;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import dev.carlosdede.expenseflow.user.mapper.UserMapper;
import dev.carlosdede.expenseflow.user.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final AddressService addressService;


    public UserService(UserRepository userRepository, UserMapper mapper, AddressService addressService){
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.addressService = addressService;
    }

    public ResponseEntity<UserResponseDTO> create(UserCreateRequestDTO userCreateRequestDTO){
        if(userRepository.existsByEmail(userCreateRequestDTO.email())){
           throw new RuntimeException("Email já está em uso");
        }

        UserEntity user = mapper.toEntity(userCreateRequestDTO);
        UserEntity savedUser = userRepository.save(user);
        addressService.createAddress(userCreateRequestDTO, savedUser);

        return null;
    }


}
