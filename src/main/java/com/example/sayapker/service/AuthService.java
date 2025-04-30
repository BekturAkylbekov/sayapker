package com.example.sayapker.service;

import com.example.sayapker.dto.request.SignInRequest;
import com.example.sayapker.dto.request.SingUpRequest;
import com.example.sayapker.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SingUpRequest userRequest);
    AuthResponse signIn(SignInRequest signInRequest);
}
