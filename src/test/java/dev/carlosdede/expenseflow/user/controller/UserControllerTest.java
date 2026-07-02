package dev.carlosdede.expenseflow.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.carlosdede.expenseflow.common.exception.EmailAlreadyExistsException;
import dev.carlosdede.expenseflow.common.exception.ResourceNotFoundException;
import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.dto.UserUpdateRequestDTO;
import dev.carlosdede.expenseflow.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        Map<String, Object> requestBody = Map.of(
                "name", "Carlos André",
                "email", "carlos@email.com",
                "phone", "11999999999",
                "password", "123456",
                "zipCode", "08065254"
        );

        when(userService.create(any(UserCreateRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated());

        verify(userService).create(any(UserCreateRequestDTO.class));
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExistsOnCreate() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "name", "Carlos André",
                "email", "carlos@email.com",
                "phone", "11999999999",
                "password", "123456",
                "zipCode", "08065254"
        );

        when(userService.create(any(UserCreateRequestDTO.class)))
                .thenThrow(new EmailAlreadyExistsException("Email já está em uso"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isConflict());

        verify(userService).create(any(UserCreateRequestDTO.class));
    }

    @Test
    void shouldFindAllUsersSuccessfully() throws Exception {
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        when(userService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userService).findAll();
    }

    @Test
    void shouldFindUserByIdSuccessfully() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        when(userService.findById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk());

        verify(userService).findById(id);
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.findById(id))
                .thenThrow(new ResourceNotFoundException("Usuário não encontrado!"));

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());

        verify(userService).findById(id);
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        Map<String, Object> requestBody = Map.of(
                "name", "Carlos André Atualizado",
                "email", "carlos.novo@email.com",
                "phone", "11988888888"
        );

        when(userService.update(eq(id), any(UserUpdateRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());

        verify(userService).update(eq(id), any(UserUpdateRequestDTO.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(userService).delete(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(userService).delete(id);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingUserDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new ResourceNotFoundException("Usuário não encontrado!"))
                .when(userService).delete(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNotFound());

        verify(userService).delete(id);
    }
}