package com.example.sayapker.service;

import com.example.sayapker.dto.request.UserRequest;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.entities.User;

import java.util.List;

public interface UserService {
    SimpleResponse updateUser(UserRequest userRequest);
    SimpleResponse deleteUser();
    List<User> getAllUsers();
}