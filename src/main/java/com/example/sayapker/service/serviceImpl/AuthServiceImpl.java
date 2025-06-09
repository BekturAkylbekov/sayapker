package com.example.sayapker.service.serviceImpl;

import com.example.sayapker.config.jwtconfig.JwtService;
import com.example.sayapker.dto.request.SignInRequest;
import com.example.sayapker.dto.request.SingUpRequest;
import com.example.sayapker.dto.response.AuthResponse;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.entities.TempUser;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final Map<String, TempUser> tempUsersByCode = new ConcurrentHashMap<>();


    @Override
    public SimpleResponse signUp(SingUpRequest userRequest) {
        if (userRepo.existsUserByEmail(userRequest.email())) {
            throw new AlreadyExistsException("Email already in use");
        }
        if (!userRequest.password().equals(userRequest.repeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        String code = String.valueOf((int)(Math.random() * 9000) + 1000);

        TempUser tempUser = new TempUser();
        tempUser.setUserName(userRequest.userName());
        tempUser.setLastName(userRequest.lastName());
        tempUser.setEmail(userRequest.email());
        tempUser.setPassword(passwordEncoder.encode(userRequest.password()));
        tempUser.setVerificationCode(code);

        tempUsersByCode.put(code, tempUser); // теперь по коду
        emailService.sendVerificationCode(userRequest.email(), code);


        return SimpleResponse.builder()
                .message("Verification code sent to email")
                .status(HttpStatus.OK)
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
    public AuthResponse confirmSignUp(String code) {
        TempUser tempUser = tempUsersByCode.get(code);
        if (tempUser == null) {
            throw new BadCredentialForbiddenException("Invalid or expired verification code.");
        }

        User user = User.builder()
                .userName(tempUser.getUserName())
                .lastName(tempUser.getLastName())
                .email(tempUser.getEmail())
                .password(tempUser.getPassword())
                .role(Role.USER)
                .verified(true)
                .build();

        userRepo.save(user);
        tempUsersByCode.remove(code);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .token(token)
                .build();
    }



}