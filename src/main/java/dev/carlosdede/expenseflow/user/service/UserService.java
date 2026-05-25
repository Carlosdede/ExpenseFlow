package dev.carlosdede.expenseflow.user.service;

import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.service.AddressService;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import dev.carlosdede.expenseflow.user.mapper.UserMapper;
import dev.carlosdede.expenseflow.user.repository.UserRepository;


import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

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


    public UserResponseDTO create(UserCreateRequestDTO userCreateRequestDTO){
        if(userRepository.existsByEmail(userCreateRequestDTO.email())){
           throw new RuntimeException("Email já está em uso");
        }

        UserEntity user = mapper.toEntity(userCreateRequestDTO);
        UserEntity savedUser = userRepository.save(user);
        AddressResponseDTO addressResponseDTO = addressService.createAddress(userCreateRequestDTO, savedUser);
        UserResponseDTO userResponseDTO = mapper.toDTO(savedUser, addressResponseDTO);
        return userResponseDTO;

    }

    public List<UserResponseDTO> findAll(){
        List<UserEntity> users = userRepository.findAll();

        return users.stream().map(user -> {AddressResponseDTO addressResponseDTO = addressService.findByUser(user);
        return mapper.toDTO(user, addressResponseDTO);
        }).toList();

    }

    public UserResponseDTO findById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usurário não encontrado!"));

        AddressResponseDTO addressResponseDTO = addressService.findByUser(user);
        return mapper.toDTO(user, addressResponseDTO);
    }
}
