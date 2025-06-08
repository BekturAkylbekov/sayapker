package com.example.sayapker.dto.response;

import com.example.sayapker.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnimalResponse {
    private String name;
    private String imageUrl;
    private Gender gender;
    private String country;
    private int price;
}
