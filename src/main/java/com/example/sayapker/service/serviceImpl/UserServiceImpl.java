package com.example.sayapker.service.serviceImpl;

import com.example.sayapker.dto.request.UserRequest;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.dto.response.UserResponse;
import com.example.sayapker.entities.User;
import com.example.sayapker.exceptions.NotFoundException;
import com.example.sayapker.repo.UserRepo;
import com.example.sayapker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public SimpleResponse updateUser(UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        User user = userRepo.findUserByPhoneNumber(phoneNumber).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        user.setUserName(userRequest.userName());
        user.setLastName(userRequest.lastName());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setEmail(userRequest.email());
        user.setImageUrl(userRequest.imageUrl());

        userRepo.save(user);

        return SimpleResponse.builder()
                .message("User updated")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber  = authentication.getName();
        User user = userRepo.findUserByPhoneNumber(phoneNumber).orElseThrow(()
                ->new NotFoundException("User not found"));

         userRepo.delete(user);

        return SimpleResponse.builder()
                .message("User deleted")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public List<UserResponse> getAllUsersAsc() {
        String sql = "SELECT user_name, last_name, phone_number, email, image_url FROM users ORDER BY user_name ASC";

        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UserResponse.builder()
                        .username(rs.getString("user_name"))
                        .lastName(rs.getString("last_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .email(rs.getString("email"))
                        .imageUrl(rs.getString("image_url"))
                        .build();
            }
        });
    }




}