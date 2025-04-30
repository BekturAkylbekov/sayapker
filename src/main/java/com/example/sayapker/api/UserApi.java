package com.example.sayapker.api;

import com.example.sayapker.dto.request.UserRequest;
import com.example.sayapker.dto.response.SimpleResponse;
import com.example.sayapker.entities.User;
import com.example.sayapker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PutMapping("/update")
    public SimpleResponse updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @DeleteMapping("/delete")
    public SimpleResponse deleteUser() {
        return userService.deleteUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
