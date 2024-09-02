package com.bikalp.library.Controller;

import com.bikalp.library.DtoConverter.UsersDto;
import com.bikalp.library.Service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto) {
        return ResponseEntity.ok().body(usersService.createUser(usersDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UsersDto> updateUser(@PathVariable Long id, @RequestBody UsersDto users) {
        return ResponseEntity.ok().body(usersService.updateUser(id, users));
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usersService.getUserById(id));
    }

    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @GetMapping("/get-all-user")
    public ResponseEntity<List<UsersDto>> getAllUser() {
        return ResponseEntity.ok().body(usersService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usersService.deleteUserById(id));
    }
}
