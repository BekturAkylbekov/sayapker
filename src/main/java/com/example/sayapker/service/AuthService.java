package com.example.sayapker.service;

import com.example.sayapker.dto.request.SignInRequest;
import com.example.sayapker.dto.request.SingUpRequest;
import com.example.sayapker.dto.response.AuthResponse;
import com.example.sayapker.dto.response.SimpleResponse;

public interface AuthService {
    SimpleResponse signUp(SingUpRequest userRequest);
    AuthResponse signIn(SignInRequest signInRequest);
    AuthResponse confirmSignUp(String code);
}
