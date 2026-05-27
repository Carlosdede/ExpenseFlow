package dev.carlosdede.expenseflow.user.service;

import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.service.AddressService;
import dev.carlosdede.expenseflow.common.exception.BusinessException;
import dev.carlosdede.expenseflow.common.exception.EmailAlreadyExistsException;
import dev.carlosdede.expenseflow.common.exception.ResourceNotFoundException;
import dev.carlosdede.expenseflow.user.dto.*;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import dev.carlosdede.expenseflow.user.mapper.UserMapper;
import dev.carlosdede.expenseflow.user.repository.UserRepository;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, UserMapper mapper, AddressService addressService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponseDTO create(UserCreateRequestDTO userCreateRequestDTO){
        if(userRepository.existsByEmail(userCreateRequestDTO.email())){
           throw new EmailAlreadyExistsException("Email já está em uso");
        }
        UserEntity user = mapper.toEntity(userCreateRequestDTO);
        String passwordHash = passwordEncoder.encode(userCreateRequestDTO.password());
        user.setPasswordHash(passwordHash);
        UserEntity savedUser = userRepository.save(user);
        AddressResponseDTO addressResponseDTO = addressService.createAddress(userCreateRequestDTO, savedUser);
        return  mapper.toDTO(savedUser, addressResponseDTO);
    }

    public List<UserResponseDTO> findAll(){
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(user -> {AddressResponseDTO addressResponseDTO = addressService.findByUser(user);
        return mapper.toDTO(user, addressResponseDTO);
        }).toList();
    }

    public UserResponseDTO findById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usurário não encontrado!"));
        AddressResponseDTO addressResponseDTO = addressService.findByUser(user);
        return mapper.toDTO(user, addressResponseDTO);
    }

    public UserResponseDTO update(UUID id, UserUpdateRequestDTO userUpdateRequestDTO){
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado!"));
        if (userUpdateRequestDTO.email() != null
                && !userUpdateRequestDTO.email().equals(user.getEmail())
                && userRepository.existsByEmail(userUpdateRequestDTO.email())) {
            throw new EmailAlreadyExistsException("Email já está em uso");
        }
        mapper.updateEntity(user, userUpdateRequestDTO);
        UserEntity updatedUser = userRepository.save(user);
        AddressResponseDTO addressResponseDTO = addressService.findByUser(updatedUser);
        return mapper.toDTO(updatedUser, addressResponseDTO );
    }

    public void delete(UUID id){
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado!"));
        userRepository.delete(user);
    }

    public void changePassword(UUID id, ChangePasswordDTO changePasswordDTO){
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado!"));

        boolean currentPasswordIsValid = passwordEncoder.matches(changePasswordDTO.currentPassword(), user.getPasswordHash());
        if (!currentPasswordIsValid){
            throw  new BusinessException("Senha atual inválida!");
        }

        boolean newPasswordIsSameAsCurrent = passwordEncoder.matches(changePasswordDTO.newPassword(), user.getPasswordHash());
        if (newPasswordIsSameAsCurrent) {
            throw new BusinessException("A nova senha deve ser diferente da senha atual!");
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    public void changeStatus(UUID id, ChangeUserStatusDTO changeUserStatusDTO){
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado!"));
        if (user.getActive().equals(changeUserStatusDTO.active())) {
            String status = changeUserStatusDTO.active() ? "ativo" : "desativado";
            throw new BusinessException("Usuário já está " + status + "!");
        }
        user.setActive(changeUserStatusDTO.active());
        userRepository.save(user);

    }
}
