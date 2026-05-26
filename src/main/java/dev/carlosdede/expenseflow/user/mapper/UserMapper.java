package dev.carlosdede.expenseflow.user.mapper;


import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.entity.AddressEntity;
import dev.carlosdede.expenseflow.address.mapper.AddressMapper;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.dto.UserUpdateRequestDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final AddressMapper addressMapper;


    public UserMapper(AddressMapper addressMapper){
        this.addressMapper = addressMapper;

    }

    public UserEntity toEntity(UserCreateRequestDTO dto){
        UserEntity entity = new UserEntity();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
        entity.setDocument(dto.document());
        return entity;
    }


    public void updateEntity(UserEntity entity,UserUpdateRequestDTO dto){
        if(dto.name() != null){
            entity.setName(dto.name());
        }
        if(dto.email() != null){
            entity.setEmail(dto.email());
        }
        if (dto.phone() != null){
            entity.setPhone(dto.phone());
        }


    }

    public UserResponseDTO toDTO(UserEntity user, AddressResponseDTO address){
        return new UserResponseDTO(
                user.getId(),
                user.getActive(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getDocument(),
                address,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}