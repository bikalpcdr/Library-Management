package com.bikalp.library.DtoConverter;

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

    @NotBlank(message = "Full name cannot be blank.")
    private String fullName;

    @Size(min = 1, max = 10)
    private String phone;

    @NotBlank(message = "address is required!")
    private String address;

    @Email(message = "Email should be valid!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    private List<Long> roles;

    // Audit fields
    private String createdBy;

    private LocalDateTime createdAt;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

}
