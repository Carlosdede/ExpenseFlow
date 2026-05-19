package dev.carlosdede.expenseflow.address.mapper;

import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressEntity toEntity(AddressRequestDTO address){
        if (address == null){
            return null;
        }
       AddressEntity entity = new AddressEntity();
        entity.setZipCode(address.zipCode());
        entity.setStreet(address.street());
        entity.setNumber(address.number());
        entity.setNeighborhood(address.neighborhood());
        entity.setCity(address.city());
        entity.setState(address.state());
        entity.setComplement(address.complement());
        return entity;
    }

    public AddressResponseDTO toDTO(AddressEntity address){
        if (address == null) {
            return null;
        }
        return new AddressResponseDTO(
                address.getZipCode(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getComplement(),
                address.getCreatedAt(),
                address.getUpdatedAt()
        );

    }

}
