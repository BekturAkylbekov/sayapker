package com.example.sayapker.dto.response;

import com.example.sayapker.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private Long id;
    private String token;
    private Role role;
}
