package com.example.sayapker.api;

import com.example.sayapker.dto.request.SignInRequest;
import com.example.sayapker.dto.request.SingUpRequest;
import com.example.sayapker.dto.response.AuthResponse;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/signUp")
    public SimpleResponse singUp(@RequestBody SingUpRequest userRequest) {
        return authService.signUp(userRequest);
    }
    @PostMapping("/signIn")
    public AuthResponse signIn(@RequestBody SignInRequest userRequest) {
        return authService.signIn(userRequest);
    }
    @PostMapping("/verify")
    public AuthResponse verify( @RequestParam String code){
        return authService.confirmSignUp(code);}
}