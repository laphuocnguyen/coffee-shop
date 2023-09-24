package com.example.demo.service;

import com.example.demo.model.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse generateToken(String username, String password);
}
