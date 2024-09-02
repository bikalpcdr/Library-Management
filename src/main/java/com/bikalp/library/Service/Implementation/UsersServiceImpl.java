package com.bikalp.library.Service.Implementation;

import com.bikalp.library.DtoConverter.UserResponseDto;
import com.bikalp.library.DTO.UsersDto;
import com.bikalp.library.DtoConverter.UsersDtoConverter;
import com.bikalp.library.Exception.UserNotFoundException;
import com.bikalp.library.Model.Users;
import com.bikalp.library.Repository.UsersRepo;
import com.bikalp.library.Service.UsersService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final UsersDtoConverter usersDtoConverter;

    public UsersServiceImpl(UsersRepo usersRepo, PasswordEncoder passwordEncoder, UsersDtoConverter usersDtoConverter) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.usersDtoConverter = usersDtoConverter;
    }

    @Override
    public UserResponseDto createUser(UsersDto usersDto) {
        usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        Users userEntity = usersDtoConverter.toEntity(usersDto);
        Users newUser = usersRepo.save(userEntity);
        return usersDtoConverter.convert(newUser);
    }

    @Override
    public UserResponseDto updateUser(Long id, UsersDto newUsersDto) {
        Users existingUser = usersRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setFullName(newUsersDto.getFullName());
        existingUser.setPhone(newUsersDto.getPhone());
        existingUser.setAddress(newUsersDto.getAddress());
        existingUser.setEmail(newUsersDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(newUsersDto.getPassword()));
        existingUser.setCreatedBy(newUsersDto.getCreatedBy());
        existingUser.setCreatedAt(newUsersDto.getCreatedAt());
        existingUser.setLastModifiedAt(newUsersDto.getLastModifiedAt());
        existingUser.setLastModifiedBy(newUsersDto.getLastModifiedBy());
        return usersDtoConverter.convert(usersRepo.save(existingUser));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Users user = usersRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return usersDtoConverter.convert(user);
    }//() -> new BookNotFoundException(id)

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<Users> users = usersRepo.findAll();
        return users.stream().map(usersDtoConverter::convert).toList();
    }

    @Override
    public boolean deleteUserById(Long id) {
        Users existingUser = usersRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        usersRepo.delete(existingUser);
        return true;
    }
}
