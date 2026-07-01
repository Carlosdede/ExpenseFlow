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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private AddressService addressService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UUID userId;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = new UserEntity();
        user.setId(userId);
        user.setName("Carlos André");
        user.setEmail("carlos@email.com");
        user.setPasswordHash("old-hash");
        user.setActive(true);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExistsOnCreate() {
        UserCreateRequestDTO dto = mock(UserCreateRequestDTO.class);

        when(dto.email()).thenReturn("carlos@email.com");
        when(userRepository.existsByEmail("carlos@email.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.create(dto));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserCreateRequestDTO requestDTO = mock(UserCreateRequestDTO.class);
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);
        AddressResponseDTO addressResponseDTO = mock(AddressResponseDTO.class);

        when(requestDTO.email()).thenReturn("carlos@email.com");
        when(requestDTO.password()).thenReturn("123456");

        when(userRepository.existsByEmail("carlos@email.com")).thenReturn(false);
        when(mapper.toEntity(requestDTO)).thenReturn(user);
        when(passwordEncoder.encode("123456")).thenReturn("hashed-password");
        when(userRepository.save(user)).thenReturn(user);
        when(addressService.createAddress(requestDTO, user)).thenReturn(addressResponseDTO);
        when(mapper.toDTO(user, addressResponseDTO)).thenReturn(responseDTO);

        UserResponseDTO result = userService.create(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        assertEquals("hashed-password", user.getPasswordHash());

        verify(userRepository).save(user);
        verify(addressService).createAddress(requestDTO, user);
        verify(mapper).toDTO(user, addressResponseDTO);
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);
        AddressResponseDTO addressResponseDTO = mock(AddressResponseDTO.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressService.findByUser(user)).thenReturn(addressResponseDTO);
        when(mapper.toDTO(user, addressResponseDTO)).thenReturn(responseDTO);

        UserResponseDTO result = userService.findById(userId);

        assertEquals(responseDTO, result);

        verify(userRepository).findById(userId);
        verify(addressService).findByUser(user);
        verify(mapper).toDTO(user, addressResponseDTO);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(userId));

        verify(addressService, never()).findByUser(any());
        verify(mapper, never()).toDTO(any(), any());
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenDeletingUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.delete(userId));

        verify(userRepository, never()).delete(any());
    }

    @Test
    void shouldChangePasswordSuccessfully() {
        ChangePasswordDTO dto = mock(ChangePasswordDTO.class);

        when(dto.currentPassword()).thenReturn("old-password");
        when(dto.newPassword()).thenReturn("new-password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old-password", "old-hash")).thenReturn(true);
        when(passwordEncoder.matches("new-password", "old-hash")).thenReturn(false);
        when(passwordEncoder.encode("new-password")).thenReturn("new-hash");

        userService.changePassword(userId, dto);

        assertEquals("new-hash", user.getPasswordHash());

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenCurrentPasswordIsInvalid() {
        ChangePasswordDTO dto = mock(ChangePasswordDTO.class);

        when(dto.currentPassword()).thenReturn("wrong-password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "old-hash")).thenReturn(false);

        assertThrows(BusinessException.class, () -> userService.changePassword(userId, dto));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldThrowExceptionWhenNewPasswordIsSameAsCurrent() {
        ChangePasswordDTO dto = mock(ChangePasswordDTO.class);

        when(dto.currentPassword()).thenReturn("old-password");
        when(dto.newPassword()).thenReturn("old-password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old-password", "old-hash")).thenReturn(true);

        assertThrows(BusinessException.class, () -> userService.changePassword(userId, dto));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldChangeStatusSuccessfully() {
        ChangeUserStatusDTO dto = mock(ChangeUserStatusDTO.class);

        when(dto.active()).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.changeStatus(userId, dto);

        assertFalse(user.getActive());

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyHasSameStatus() {
        ChangeUserStatusDTO dto = mock(ChangeUserStatusDTO.class);

        when(dto.active()).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> userService.changeStatus(userId, dto));

        verify(userRepository, never()).save(any());
    }
}