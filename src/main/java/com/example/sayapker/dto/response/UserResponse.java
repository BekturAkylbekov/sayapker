package com.example.sayapker.dto.response;

import com.example.sayapker.entities.Animal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String username;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String imageUrl;
    private List<Animal> animals;

    public UserResponse(String username, String lastName, String phoneNumber, String email, String imageUrl, List<Animal> animals) {
        this.username = username;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageUrl = imageUrl;
        this.animals = animals;
    }
}
