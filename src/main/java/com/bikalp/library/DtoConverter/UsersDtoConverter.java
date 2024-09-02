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
        // Password should be handled with care; consider setting it elsewhere if needed.
        for (Long roleId : dto.getRoles()) {
            entity.addRole(Role.builder().id(roleId).build());
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
        dto.setRoles(entity.getRoles().stream().map(Role::getId).collect(Collectors.toList()));

        return dto;
    }
}
