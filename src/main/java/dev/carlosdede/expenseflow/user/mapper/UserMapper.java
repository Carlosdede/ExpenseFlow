package dev.carlosdede.expenseflow.user.mapper;


import dev.carlosdede.expenseflow.user.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserCreateRequestDTO dto){
        UserEntity entity = new UserEntity();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
        entity.setDocument(dto.document());
        entity.setZipCode(dto.addressRequestDTO().zipCode());
        entity.setNumber(dto.addressRequestDTO().number());
        entity.setComplement(dto.addressRequestDTO().complement());
        return entity;
    }

    public UserResponseDTO toDTO(UserEntity entity){
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO(
                entity.getZipCode(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getComplement()
        );
        return new UserResponseDTO(
                entity.getId(),
                entity.getActive(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDocument(),
                addressResponseDTO,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}