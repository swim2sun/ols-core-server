package com.thoughtworks.nho.api;

import com.thoughtworks.nho.cofiguration.security.JWTUser;
import com.thoughtworks.nho.cofiguration.security.LoginRequestUser;
import com.thoughtworks.nho.domain.User;
import com.thoughtworks.nho.service.AuthService;
import com.thoughtworks.nho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public JWTUser login(@RequestBody LoginRequestUser loginRequestUser, HttpServletResponse response) {
        return authService.login(response, loginRequestUser);
    }

    @PostMapping("/regist")
    @ResponseStatus(HttpStatus.OK)
    public JWTUser regist(@RequestBody @Valid LoginRequestUser loginRequestUser, HttpServletResponse response) {
        User user = User.builder().name(loginRequestUser.getUsername()).password(loginRequestUser.getPassword()).build();
        userService.create(user);
        return authService.login(response, loginRequestUser);
    }
}
