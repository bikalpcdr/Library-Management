package com.bikalp.library.Service.Implementation;

import com.bikalp.library.DtoConverter.UsersDto;
import com.bikalp.library.DtoConverter.UsersDtoConverter;
import com.bikalp.library.Exception.UserNotFoundException;
import com.bikalp.library.Model.Users;
import com.bikalp.library.Repository.UsersRepo;
import com.bikalp.library.Service.UsersService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public UsersDto createUser(UsersDto usersDto) {
        usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        Users userEntity = usersDtoConverter.toEntity(usersDto);
        Users newUser = usersRepo.save(userEntity);
        return usersDtoConverter.toDto(newUser);
    }

    @Override
    public UsersDto updateUser(Long id, UsersDto newUsersDto) {
        Users existingUser = usersRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setFullName(newUsersDto.getFullName());
        existingUser.setPhone(newUsersDto.getPhone());
        existingUser.setAddress(newUsersDto.getAddress());
        existingUser.setEmail(newUsersDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(newUsersDto.getPassword()));

        Users updatedUser = usersRepo.save(existingUser);
        return usersDtoConverter.toDto(updatedUser);
    }

    @Override
    public UsersDto getUserById(Long id) {
        Users user = usersRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return usersDtoConverter.toDto(user);
    }

    @Override
    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepo.findAll();
        return users.stream().map(usersDtoConverter::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserById(Long id) {
        Users existingUser = usersRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        usersRepo.deleteById(id);
        return true;
    }
}
