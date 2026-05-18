package dev.carlosdede.expenseflow.user.mapper;


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
        return entity;
    }

    public UserResponseDTO toDTO(UserEntity entity){
        return new UserResponseDTO(
                entity.getId(),
                entity.getActive(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDocument(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}