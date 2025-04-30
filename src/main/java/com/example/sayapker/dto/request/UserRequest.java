package com.example.sayapker.dto.request;

import lombok.Builder;

@Builder
public record UserRequest(
        String userName,
        String lastName,
        String phoneNumber,
        String email,
        String imageUrl
        ) {

}
