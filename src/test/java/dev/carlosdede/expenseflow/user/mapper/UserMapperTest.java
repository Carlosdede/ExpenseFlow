package dev.carlosdede.expenseflow.user.mapper;
import dev.carlosdede.expenseflow.address.dto.AddressRequestDTO;
import dev.carlosdede.expenseflow.address.mapper.AddressMapper;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp(){
        AddressMapper addressMapper = new AddressMapper();
        userMapper = new UserMapper(addressMapper);
    }

    @Test
    public void shouldMapUserCreateRequestDtoToUserEntity() {


        UserCreateRequestDTO userCreateRequestDTO = new UserCreateRequestDTO(
                "Carlos André",
                "carlos@email.com",
                "11999999999",
                "123456",
                "080652544",
                null


        );

        UserEntity entity = userMapper.toEntity(userCreateRequestDTO);

        assertNotNull(entity);
        assertEquals("Carlos André", entity.getName());
        assertEquals("carlos@email.com", entity.getEmail());
        assertNull(entity.getPasswordHash());
        assertEquals("123456", entity.getPhone());
        assertEquals("080652544", entity.getDocument());



    }
}
