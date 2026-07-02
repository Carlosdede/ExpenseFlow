package dev.carlosdede.expenseflow.user.controller;

import dev.carlosdede.expenseflow.user.dto.UserCreateRequestDTO;
import dev.carlosdede.expenseflow.user.dto.UserResponseDTO;
import dev.carlosdede.expenseflow.user.dto.UserUpdateRequestDTO;
import dev.carlosdede.expenseflow.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO){
        UserResponseDTO user = userService.create(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id){
        UserResponseDTO user = userService.findById(id);
        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid UserUpdateRequestDTO userUpdateRequestDTO){
        UserResponseDTO user = userService.update(id, userUpdateRequestDTO);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
