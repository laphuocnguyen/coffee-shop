package com.example.demo.security;

import com.example.demo.entities.ShopUser;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ShopUser> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()) {
            return new User(userOpt.get().getUsername(), userOpt.get().getPassword(), Collections.emptyList());
        }
        throw new UsernameNotFoundException("User not found with login: " + username);
    }

}