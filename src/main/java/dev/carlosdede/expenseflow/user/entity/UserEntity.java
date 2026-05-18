package dev.carlosdede.expenseflow.user.entity;


import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


@Entity
@Table(name ="users")

public class UserEntity {
    @Id
    @Column(name ="id" )
    private UUID id;

    @Column(name = "name",length = 120, nullable = false)
    @NotNull
    private String name;

    @Column(name = "email", length = 150, nullable = false, unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "password_hash",length = 255, nullable = false)
    @NotNull
    private String passwordHash;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "document", length = 20)
    private String document;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @NotNull
    private LocalDateTime updatedAt;

}
