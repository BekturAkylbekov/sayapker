package com.example.sayapker.dto.request;

import com.example.sayapker.enums.Category;
import com.example.sayapker.enums.Gender;
import com.example.sayapker.enums.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class AnimalRequest {
    private String imageUrl;
    private String name;
    private LocalDate birthday;
    private String color;
    private Gender gender;
    private Category category;
    private String father;
    private String mother;
    private String country;
    private String extraInfo;
    private String nameOwner;
    private Payment payment;
    private int price;
}
