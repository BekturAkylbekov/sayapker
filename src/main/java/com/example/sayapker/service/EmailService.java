package com.example.sayapker.service;

public interface EmailService {
    void sendVerificationCode(String to, String code);
}
