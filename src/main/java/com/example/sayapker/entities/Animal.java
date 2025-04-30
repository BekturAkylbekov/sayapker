package com.example.sayapker.entities;

import com.example.sayapker.enums.Category;
import com.example.sayapker.enums.Gender;
import com.example.sayapker.enums.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    private String imageUrl;
    private String name;
    private LocalDate birthday;
    private String color;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String fatherId;
    private String motherId;
    private String country;
    private String extraInfo;
    @ManyToOne
    private User user;
    private String nameOwner;
    @Enumerated(EnumType.STRING)
    private Payment payment;
}
