package dev.carlosdede.expenseflow.address.mapper;

import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import dev.carlosdede.expenseflow.address.dto.AddressResponseDTO;
import dev.carlosdede.expenseflow.address.entity.AddressEntity;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp(){
        addressMapper = new AddressMapper();
    }

    @Test
    void shouldMapAddressRequestDtoToAddressEntity(){
        AddressRequestDTO dto = new AddressRequestDTO(
                "12345-678",
                "Rua Teste",
                "100",
                "Centro",
                "São Paulo",
                "SP",
                "Apto 10"
        );

        AddressEntity entity = addressMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("12345-678", entity.getZipCode());
        assertEquals("Rua Teste", entity.getStreet());
        assertEquals("100", entity.getNumber());
        assertEquals("Centro", entity.getNeighborhood());
        assertEquals("São Paulo", entity.getCity());
        assertEquals("SP", entity.getState());
        assertEquals("Apto 10", entity.getComplement());
    }

    @Test
    void shouldMapAddressEntityToAddressResponseDto() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        AddressEntity entity = new AddressEntity();
        entity.setId(id);
        UserEntity user = new UserEntity();
        user.setId(userId);
        entity.setZipCode("12345-678");
        entity.setStreet("Rua Teste");
        entity.setNumber("100");
        entity.setNeighborhood("Centro");
        entity.setCity("São Paulo");
        entity.setState("SP");
        entity.setComplement("Apto 10");

        AddressResponseDTO response = addressMapper.toDTO(entity);

        assertNotNull(response);
        assertEquals("12345-678", response.zipCode());
        assertEquals("Rua Teste", response.street());
        assertEquals("100", response.number());
        assertEquals("Centro", response.neighborhood());
        assertEquals("São Paulo", response.city());
        assertEquals("SP", response.state());
        assertEquals("Apto 10", response.complement());
    }
}
