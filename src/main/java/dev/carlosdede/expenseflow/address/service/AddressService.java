package dev.carlosdede.expenseflow.address.service;

import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.entity.AddressEntity;
import dev.carlosdede.expenseflow.address.mapper.AddressMapper;
import dev.carlosdede.expenseflow.address.repository.AddressRepository;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import org.springframework.stereotype.Service;


@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper){
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressResponseDTO createAddress(UserCreateRequestDTO userCreateRequestDTO, UserEntity savedUser) {
        if(userCreateRequestDTO.addressRequestDTO() != null){
            AddressEntity address = addressMapper.toEntity(userCreateRequestDTO.addressRequestDTO());
            address.setUser(savedUser);
            AddressEntity newAddress = addressRepository.save(address);

            return addressMapper.toDTO(newAddress);

        }
        return null;
    }

    public AddressResponseDTO findByUser(UserEntity id){
        return null;
    }

}
