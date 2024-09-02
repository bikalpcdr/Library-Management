package com.bikalp.library.DtoConverter;

import com.bikalp.library.Model.Role;
import com.bikalp.library.Model.Users;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsersDtoConverter {

    public Users toEntity(UsersDto dto) {
        if (dto == null) return null;

        Users entity = new Users();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        // Handle password securely; consider setting it elsewhere if needed
        entity.setPassword(dto.getPassword());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setLastModifiedAt(dto.getLastModifiedAt());

        // Map roles from IDs to Role objects
        if (dto.getRoles() != null) {
            entity.setRoles(dto.getRoles().stream().map(roleId -> Role.builder().id(roleId).build()).collect(Collectors.toSet()));
        }

        return entity;
    }

    public UsersDto toDto(Users entity) {
        if (entity == null) return null;

        UsersDto dto = new UsersDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword()); // Ensure secure handling
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedAt(entity.getLastModifiedAt());

        // Map roles from Role objects to IDs
        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        }

        return dto;
    }
}
