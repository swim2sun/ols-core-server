package com.thoughtworks.nho.cofiguration.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestUser {
    @Pattern(regexp = "[a-zA-Z0-9@]+", message = "user")
    private String username;
    private String password;
}
