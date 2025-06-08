package com.example.sayapker.dto.request;

import lombok.Builder;

@Builder
public record SingUpRequest (
   String userName,
   String lastName,
   String email,
   String password,
   String repeatPassword
){
}
