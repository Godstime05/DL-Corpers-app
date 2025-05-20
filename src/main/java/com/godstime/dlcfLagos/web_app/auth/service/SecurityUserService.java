package com.godstime.dlcfLagos.web_app.auth.service;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.godstime.dlcfLagos.web_app.dto.Request;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolationException;

@Service
public class SecurityUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public SecurityUserService(UserRepository userRepository, 
                             PasswordEncoder passwordEncoder, 
                             JwtUtils jwtUtils,
                             AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public Request register(Request request) {
        Request response = new Request();
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setRole(request.getRole());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            if(user.getRole() != null && !user.getRole().equals("USER") && !user.getRole().equals("ADMIN")) {
                response.setStatusCode(400);
                response.setError("Invalid role");  
                response.setMessage("Role must be either USER or ADMIN");
                return response;
            }

            User savedUser = userRepository.save(user);
            if(savedUser.getId() > 0) {
                response.setStatusCode(200);
                response.setMessage("User registered successfully");
                response.setUser(savedUser);
            }
        } catch (ConstraintViolationException e) {
            StringBuilder errorMsg = new StringBuilder("Validation failed: ");
            e.getConstraintViolations().forEach(violation -> {
                errorMsg.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append("; ");
            });
            response.setStatusCode(400);
            response.setError("Validation error");
            response.setMessage(errorMsg.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setError("Internal server error");
            response.setMessage("User registration failed");
            return response;
        }
        return response;
    }

    public Request login(Request request) {
        Request response = new Request();
        try {
            var userOptional = userRepository.findByEmail(request.getEmail());

            if(userOptional.isEmpty()) {
                System.out.println("Login failed: User not found with email: " + request.getEmail());
                response.setStatusCode(401);
                response.setError("Authentication failed");
                response.setMessage("Invalid email or password");
                return response;
            }
            var user = userOptional.get();

            try {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), 
                    request.getPassword()
                );

                var authentication = authenticationManager.authenticate(authenticationToken);
                if(!authentication.isAuthenticated()) {
                    System.out.println("Login failed: User not authenticated with email: " + request.getEmail());
                    response.setStatusCode(401);
                    response.setError("Authentication failed");
                    response.setMessage("Invalid email or password");
                    return response;
                }

            } catch (Exception e) {
                System.out.println("Login failed: Authentication exception for user:" + request.getEmail() + ", Error: " + e.getMessage());
                response.setStatusCode(401);
                response.setError("Authentication failed");
                response.setMessage("Invalid email or password");
                return response;
            }

            System.out.println("Login successful for user: " + request.getEmail());
            var token = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            
            response.setToken(token);
            response.setRefreshToken(refreshToken);
            response.setStatusCode(200);
            response.setRole(user.getRole());
            response.setExpirationTime("24 Hours");
            response.setMessage("User Logged in successfully");
            response.setUser(user);

        } catch(Exception e) {
            System.out.println("Login error: Unexpected exception: " + e.getMessage());
            response.setStatusCode(500);
            response.setError("Internal Server Error");
            response.setMessage("An unexpected error has occurred");
        }
        return response;
    }

    public Request refreshToken(Request refreshTokenRequest) {
        Request response = new Request();
        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = userRepository.findByEmail(email).orElseThrow();

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                var token = jwtUtils.generateToken(user);
                response.setStatusCode(200);
                response.setToken(token);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24 Hours");
                response.setMessage("Token refreshed successfully");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}   
