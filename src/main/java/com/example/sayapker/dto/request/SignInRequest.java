package com.example.sayapker.dto.request;

public record SignInRequest(
        String phoneNumber,
        String password
) {
}
