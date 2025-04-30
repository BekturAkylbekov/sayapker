package com.example.sayapker.service.serviceImpl;

import com.example.sayapker.config.jwtconfig.JwtService;
import com.example.sayapker.dto.request.SignInRequest;
import com.example.sayapker.dto.request.SingUpRequest;
import com.example.sayapker.dto.response.AuthResponse;
import com.example.sayapker.entities.User;
import com.example.sayapker.enums.Role;
import com.example.sayapker.exceptions.AlreadyExistsException;
import com.example.sayapker.exceptions.BadCredentialForbiddenException;
import com.example.sayapker.exceptions.NotFoundException;
import com.example.sayapker.repo.UserRepo;
import com.example.sayapker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse signUp(SingUpRequest userRequest) {
        if (userRepo.existsUserByPhoneNumber(userRequest.phoneNumber())) {
            throw new AlreadyExistsException("Phone number already in use");
        }
        if (!userRequest.password().equals(userRequest.repeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (!userRequest.phoneNumber().startsWith("+996")){
            throw new NotFoundException("Phone number must start with +996");
        }

        User user = User.builder()
                .userName(userRequest.userName())
                .lastName(userRequest.lastName())
                .phoneNumber(userRequest.phoneNumber())
                .password(passwordEncoder.encode(userRequest.password()))
                .role(Role.USER)
                .build();

        userRepo.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = userRepo.findUserByPhoneNumber(signInRequest.phoneNumber()).orElseThrow(()
                -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialForbiddenException("password incorrect");
        }

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .token(token)
                .build();
    }
}