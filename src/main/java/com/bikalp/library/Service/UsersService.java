package com.bikalp.library.Service;

import com.bikalp.library.DtoConverter.UsersDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    UsersDto createUser(UsersDto usersDto);

    UsersDto updateUser(Long id, UsersDto usersDto);

    UsersDto getUserById(Long id);

    List<UsersDto> getAllUsers();

    boolean deleteUserById(Long id);
}
