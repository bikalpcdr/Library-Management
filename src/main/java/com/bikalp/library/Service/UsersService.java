package com.bikalp.library.Service;

import com.bikalp.library.DtoConverter.UserResponseDto;
import com.bikalp.library.DTO.UsersDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    UserResponseDto createUser(UsersDto usersDto);

    UserResponseDto updateUser(Long id, UsersDto usersDto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    boolean deleteUserById(Long id);
}
