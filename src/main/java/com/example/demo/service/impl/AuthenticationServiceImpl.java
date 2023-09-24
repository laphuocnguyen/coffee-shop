package com.example.demo.service.impl;

import com.example.demo.exception.BusinessException;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.security.JwtService;
import com.example.demo.security.MyUserDetailsService;
import com.example.demo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailService;
    private final JwtService jwtService;

    public AuthenticationResponse generateToken(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex) {
            throw new BusinessException("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }
        final UserDetails user = userDetailService.loadUserByUsername(username);
        final String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}