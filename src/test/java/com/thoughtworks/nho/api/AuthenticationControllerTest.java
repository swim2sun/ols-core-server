package com.thoughtworks.nho.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.nho.cofiguration.security.LoginRequestUser;
import com.thoughtworks.nho.domain.User;
import com.thoughtworks.nho.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends BaseControllerTest {
    @Autowired
    private UserService userService;

    @Override
    @BeforeEach
    void setup() {
        super.setup();
        userService.create(User.builder().name("testUser").password("123").build());
    }

    @Test
    void should_login_successfully() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("testUser").password("123").build();

        mockMvc.perform(post("/api/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void should_login_failed_when_login_with_bad_credential() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("wrong_username").password("wrong_password").build();

        mockMvc.perform(post("/api/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_regist_successfully() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("newUser").password("123").build();

        mockMvc.perform(post("/api/authentication/regist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newUser"));
    }

    @Test
    void should_register_failed_when_username_contains_special_character() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("test.&*").password("123").build();

        mockMvc.perform(post("/api/authentication/regist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void should_register_failed_when_username_length_greater_then_20() throws Exception {
        String username = "thisIsALongUsername@@@@@@@@@@@@";
        assertThat(username.length()).isGreaterThan(20);
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username(username).password("123").build();

        mockMvc.perform(post("/api/authentication/regist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void should_register_failed_when_username_is_used() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("testUser").password("123").build();

        mockMvc.perform(post("/api/authentication/regist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void should_register_failed_when_password_contains_special_character() throws Exception {
        LoginRequestUser loginRequestBody = LoginRequestUser.builder()
                .username("newUser").password("123*").build();

        mockMvc.perform(post("/api/authentication/regist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestBody)))
                .andExpect(status().is4xxClientError());
    }

}