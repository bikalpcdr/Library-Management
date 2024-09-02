package com.bikalp.library.DtoConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private Long id;

    @Column(name = "full_name",nullable = false, length = 50)
    private String fullName;

    @Size(min = 1, max = 10)
    private String phone;

    @NotBlank(message = "address is required!")
    private String address;

    @Email(message = "Email should be valid!")
    @NotBlank(message = "Email is required!")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @JsonIgnore
    private List<Long> roles;

    // Audit fields
    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private String lastModifiedBy;

    @JsonIgnore
    private LocalDateTime lastModifiedAt;

}
