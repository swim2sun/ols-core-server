package com.thoughtworks.nho.cofiguration.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestUser {
    @Pattern(regexp = "[a-zA-Z0-9@]+", message = "user")
    @Length(max = 20)
    private String username;
    private String password;
}
