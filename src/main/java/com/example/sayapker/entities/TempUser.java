package com.example.sayapker.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempUser {
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private String verificationCode;

}
