package com.godstime.dlcfLagos.web_app.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godstime.dlcfLagos.web_app.auth.service.SecurityUserService;
import com.godstime.dlcfLagos.web_app.dto.Request;
import com.godstime.dlcfLagos.web_app.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SecurityUserService userService;

    @Test
    public void testRegister_Success() throws Exception {
        // Prepare test data
        Request registerRequest = new Request();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setRole("USER");
        registerRequest.setPhoneNumber("1234567890");

        // Mock service response
        Request response = new Request();
        response.setStatusCode(200);
        response.setMessage("User registered successfully");
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        response.setUser(user);

        when(userService.register(any(Request.class))).thenReturn(response);

        // Perform test
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.username").value("testuser"))
                .andExpect(jsonPath("$.user.email").value("test@example.com"));
    }

    @Test
    public void testLogin_Success() throws Exception {
        // Prepare test data
        Request loginRequest = new Request();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        // Mock service response
        Request response = new Request();
        response.setStatusCode(200);
        response.setMessage("User Logged in successfully");
        response.setToken("jwt-token");
        response.setRefreshToken("refresh-token");
        response.setExpirationTime("24 Hours");

        when(userService.login(any(Request.class))).thenReturn(response);

        // Perform test
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("User Logged in successfully"))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.expirationTime").value("24 Hours"));
    }

    @Test
    public void testRefreshToken_Success() throws Exception {
        // Prepare test data
        Request refreshRequest = new Request();
        refreshRequest.setToken("old-refresh-token");

        // Mock service response
        Request response = new Request();
        response.setStatusCode(200);
        response.setMessage("Token refreshed successfully");
        response.setToken("new-jwt-token");
        response.setRefreshToken("old-refresh-token");
        response.setExpirationTime("24 Hours");

        when(userService.refreshToken(any(Request.class))).thenReturn(response);

        // Perform test
        mockMvc.perform(post("/api/auth/refreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Token refreshed successfully"))
                .andExpect(jsonPath("$.token").value("new-jwt-token"))
                .andExpect(jsonPath("$.refreshToken").value("old-refresh-token"))
                .andExpect(jsonPath("$.expirationTime").value("24 Hours"));
    }
} 