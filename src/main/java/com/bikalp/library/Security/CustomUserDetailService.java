package com.bikalp.library.Security;

import com.bikalp.library.Repository.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UsersRepo usersRepo;

    public CustomUserDetailService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Loading user from database (Here email as a username)
        return this.usersRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
