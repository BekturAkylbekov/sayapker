package com.example.sayapker.service.serviceImpl;

import com.example.sayapker.config.jwtconfig.JwtService;
import com.example.sayapker.dto.request.SignInRequest;
import com.example.sayapker.dto.request.SingUpRequest;
import com.example.sayapker.dto.response.AuthResponse;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.entities.User;
import com.example.sayapker.enums.Role;
import com.example.sayapker.exceptions.AlreadyExistsException;
import com.example.sayapker.exceptions.BadCredentialForbiddenException;
import com.example.sayapker.exceptions.NotFoundException;
import com.example.sayapker.repo.UserRepo;
import com.example.sayapker.service.AuthService;
import com.example.sayapker.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public AuthResponse signUp(SingUpRequest userRequest) {
        if (userRepo.existsUserByEmail(userRequest.email())) {
            throw new AlreadyExistsException("email already in use");
        }
        if (!userRequest.password().equals(userRequest.repeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        String code = String.valueOf((int)(Math.random() * 9000) + 1000);

        User user = User.builder()
                .userName(userRequest.userName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .role(Role.USER)
                .verificationCode(code)
                .verified(false)
                .build();
        emailService.sendVerificationCode(userRequest.email(), code);
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
        User user = userRepo.findUserByEmail(signInRequest.email())
                .orElseThrow(() -> {
                    log.info("User with phone number {} not found", signInRequest.email());
                    return new NotFoundException(String.format("User with email %s not found", signInRequest.email()));
                });

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

    @Override
    public SimpleResponse verifyEmail(String email, String code) {
        User user = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getVerificationCode().equals(code)) {
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepo.save(user);
            return SimpleResponse.builder()
                    .message("Email verified successfully")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            throw new BadCredentialForbiddenException("Invalid verification code.");
        }
    }


}